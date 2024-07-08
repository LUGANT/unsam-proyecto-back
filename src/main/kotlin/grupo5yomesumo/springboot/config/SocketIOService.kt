package grupo5yomesumo.springboot.config

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class SocketIOService {

    private val config = Configuration().apply {
        hostname = "localhost"
        port = 9092
//        storeFactory = Mongo
    }

    private val server = SocketIOServer(config)

    @PostConstruct
    fun startServer() {
        server.addEventListener("chat message", ChatMessage::class.java, DataListener<ChatMessage> { client, data, ackRequest ->
            // handle the event
            println("Received message: ${data.text}")
            // broadcast the message to all connected clients
            broadcastMessage("chat message", data)
        })

        server.start()
        println("Socket.IO server started on port ${config.port}")
        server.
    }

    @PreDestroy
    fun stopServer() {
        server.stop()
        println("Socket.IO server stopped")
    }

    fun broadcastMessage(event: String, data: Any) {
        server.allClients.forEach { client ->
            client.sendEvent(event, data)
        }
    }

    data class ChatMessage(
        val text: String = "",
//        val date: LocalDateTime = LocalDateTime.now(),
        val userId: Long = 0L
    )

}