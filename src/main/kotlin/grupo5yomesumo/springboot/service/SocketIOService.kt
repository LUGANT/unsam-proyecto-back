package grupo5yomesumo.springboot.service

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.stereotype.Service

@Service
class SocketIOService {

    private val config = Configuration().apply {
        hostname = "localhost"
        port = 9092
    }

    private val server = SocketIOServer(config)

    @PostConstruct
    fun startServer() {
        server.addEventListener("chat message", ChatMessage::class.java, ChatMessageEventListener(server))
        server.addEventListener(
            "joinRoom", String::class.java
        ) { client, roomName, ackSender ->
            client.joinRoom(roomName)
            println("Client " + client.sessionId + " joined room: " + roomName)
        }

        server.addEventListener(
            "leaveRoom", String::class.java
        ) { client, roomName, ackSender ->
            client.leaveRoom(roomName)
            println("Client " + client.sessionId + " left room: " + roomName)
        }

        server.start()
        println("Socket.IO server started on port ${config.port}")
    }

    @PreDestroy
    fun stopServer() {
        server.stop()
        println("Socket.IO server stopped")
    }

}

class ChatMessageEventListener(
    val server: SocketIOServer
): DataListener<ChatMessage> {

    override fun onData(client: SocketIOClient, message: ChatMessage, ack: AckRequest) {
        println("Received message: ${message!!.text}")
        server.getRoomOperations(message.room).sendEvent("chat message", message)
//        broadcastMessage("chat message", message!!)
    }

    fun broadcastMessage(event: String, data: ChatMessage) {
        server.allClients.forEach { client ->
            client.sendEvent(event, data)
        }
    }

}


data class ChatMessage(
    val text: String = "",
//        val date: LocalDateTime = LocalDateTime.now(),
    val room: String = "",
    val usuarioId: Long = 0L,
)