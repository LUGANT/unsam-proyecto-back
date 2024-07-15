package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.listeners.*
import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketIOServer
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
        addListeners()

        server.start()
        println("Socket.IO server started on port ${config.port}")
    }

    @PreDestroy
    fun stopServer() {
        server.stop()
        println("Socket.IO server stopped")
    }

    fun addListeners(){
        server.addEventListener("chat message", ChatMessage::class.java, MessageEventListener(server))
        server.addEventListener("joinRoom", String::class.java, JoinRoomListener())
        server.addEventListener("leaveRoom", String::class.java, LeaveRoomListener())
    }

}

data class ChatMessage(
    val text: String = "",
//        val date: LocalDateTime = LocalDateTime.now(),
    val room: String = "",
    val usuarioId: Long = 0L,
)