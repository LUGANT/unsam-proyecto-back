package grupo5yomesumo.springboot.listeners

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import grupo5yomesumo.springboot.service.ChatMessage

class MessageEventListener(
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