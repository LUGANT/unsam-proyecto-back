package grupo5yomesumo.springboot.config

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.changestream.OperationType
import grupo5yomesumo.springboot.domain.Chat
import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.repository.ChatRepository
import grupo5yomesumo.springboot.repository.MensajeRepository
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Service
import java.util.concurrent.Executors


@Service
class SocketIOService (
    var mensajeRepository : MensajeRepository,
    var chatRepository: ChatRepository
) {

    private val config = Configuration().apply {
        hostname = "localhost"
        port = 9092
    }



    private val server = SocketIOServer(config)
    private val mongoClient = MongoClients.create("mongodb://localhost:27021")
    private val database: MongoDatabase = mongoClient.getDatabase("yomesumo-mensajes")
    private val collection: MongoCollection<org.bson.Document> = database.getCollection("mensaje")
    private val executor = Executors.newSingleThreadExecutor()

    @PostConstruct
    fun startServer() {
        server.addEventListener("chat message", ChatMensajeDTO::class.java) { client, data, ackRequest ->

            //Busca el chat existente, sino crea uno
            val chat = chatRepository.findByEventoIdOrUserIds(data.eventoId, data.userIds) ?: Chat(
                eventoId = data.eventoId,
                userIds = data.userIds.toList(),
                mensajes = mutableListOf()
            )

//

            // Crea el mensaje
            val mensaje = Mensaje(
                usuarioId = data.usuarioId,
//                horario = data.horario,
                texto = data.texto
            )

            // Agrega el mensaje al chat
            chat.mensajes.add(mensaje)

            // Guarda el chat
            chatRepository.save(chat)

            println("Mensaje Recibido: ${chat.mensajes.last().texto} at --")

            broadcastMessage("chat message", data)

            // Start Change Stream listener
            executor.submit { listenForChanges() }
        }

        server.start()
        println("Socket.IO server started on port ${config.port}")
    }

    private fun listenForChanges() {
        val changeStream = database.getCollection("chat").watch().iterator()
        while (changeStream.hasNext()) {
            val change = changeStream.next()
            if (change.operationType == OperationType.INSERT || change.operationType == OperationType.UPDATE) {
                val fullDocument = change.fullDocument ?: continue
                val mensajesDocument = fullDocument.getList("mensajes", org.bson.Document::class.java)
                val mensajes = mensajesDocument.map { mensajeDoc ->
                    Mensaje(
                        texto = mensajeDoc.getString("texto"),
                        usuarioId = mensajeDoc.getLong("usuarioId")
                        // Añadir cualquier otro campo necesario
                    )
                }
                val chatMensajeDTO = ChatMensajeDTO(
                    texto = mensajes.last().texto,
//                    horario = LocalDateTime.parse(fullDocument.getString("horario")),
                    usuarioId = mensajes.last().usuarioId,
                    eventoId = fullDocument.getLong("eventoId"),
                    userIds = fullDocument.getList("userIds", Long::class.java)
                )
                server.allClients.forEach { client ->
                    client.sendEvent("chat message", chatMensajeDTO)
                }
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