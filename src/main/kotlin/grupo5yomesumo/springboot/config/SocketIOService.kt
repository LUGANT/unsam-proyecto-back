package grupo5yomesumo.springboot.config

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.changestream.OperationType
import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.repository.MensajeRepository
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.bson.Document
import org.springframework.stereotype.Service
import java.util.concurrent.Executors


@Service
class SocketIOService (
    var mensajeRepository: MensajeRepository
) {

    private val mongoClient = MongoClients.create("mongodb://mongo:mongo@localhost:27021")
    private val database: MongoDatabase = mongoClient.getDatabase("yomesumo-mensajes")
    private val collection: MongoCollection<Document> = database.getCollection("mensaje")
    private val executor = Executors.newSingleThreadExecutor()

    private val config = Configuration().apply {
        hostname = "localhost"
        port = 9092
    }



    private val server = SocketIOServer(config)


    @PostConstruct
    fun startServer() {

        server.addEventListener("eventoId", Long::class.java) { client, data, ackRequest ->
            val mensajesAnteriores = mensajeRepository.getMensajesByEventoId(data)
            if (mensajesAnteriores.isNotEmpty()) {
                client.sendEvent("initial messages", mensajesAnteriores)
            }
        }

        server.addEventListener("chat message", ChatMensajeDTO::class.java) { client, data, ackRequest ->

            // Crea el mensaje
            val mensaje = Mensaje(
                usuarioId = data.usuarioId,
                eventoId = data.eventoId,
//                horario = data.horario,
                texto = data.texto
            )

            mensajeRepository.save(mensaje)

            broadcastMessage("chat message", data)

//            // Start Change Stream listener
//            listenForChanges()
        }

        server.start()
        println("Socket.IO server started on port ${config.port}")
    }

    private fun listenForChanges() {
        val changeStream = database.getCollection("mensaje").watch().iterator()
        println("Listening for changes...")
        while (changeStream.hasNext()) {
            val change = changeStream.next()
            println("Change detected: $change")
            if (change.operationType == OperationType.INSERT || change.operationType == OperationType.UPDATE) {
                val fullDocument = change.fullDocument ?: continue
                val mensajesDocument = fullDocument.getList("mensajes", Document::class.java).map { doc ->
                    ChatMensajeDTO(
                        texto = doc.getString("texto"),
                        usuarioId = doc.getLong("usuarioId"),
                        eventoId = fullDocument.getLong("eventoId"),
                        userIds = fullDocument.getList("userIds", Long::class.java)
                    )
                }
//                val mensajes = mensajesDocument.map { mensajeDoc ->
//                    Mensaje(
//                        texto = mensajeDoc.getString("texto"),
//                        usuarioId = mensajeDoc.getLong("usuarioId")
//                        // Añadir cualquier otro campo necesario
//                    )
//                }
//                val chatMensajeDTO = ChatMensajeDTO(
//                    texto = mensajes.last().texto,
////                    horario = LocalDateTime.parse(fullDocument.getString("horario")),
//                    usuarioId = mensajes.last().usuarioId,
//                    eventoId = fullDocument.getLong("eventoId"),
//                    userIds = fullDocument.getList("userIds", Long::class.java)
//                )

                mensajesDocument.forEach { message ->
                    println("Sending change: $message")
                    server.allClients.forEach { client ->
                        client.sendEvent("chat message", message)
                    }
                }

//                server.allClients.forEach { client ->
//                    client.sendEvent("chat message", message)
//                }
            }
        }
    }

    @PreDestroy
    fun stopServer() {
        server.stop()
        mongoClient.close()
        executor.shutdown()
        println("Socket.IO server stopped")
    }

    fun broadcastMessage(event: String, data: Any) {
        server.allClients.forEach { client ->
            client.sendEvent(event, data)
        }
    }

    data class ChatMensajeDTO(
        val texto: String = "",
//        val horario: LocalDateTime,
        val usuarioId: Long = 0,
        val eventoId: Long = 0,
        val userIds: List<Long> = mutableListOf()
    )

}