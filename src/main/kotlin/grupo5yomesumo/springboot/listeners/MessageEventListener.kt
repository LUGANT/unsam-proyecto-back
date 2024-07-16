package grupo5yomesumo.springboot.listeners

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.DataListener
import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.domain.exceptions.GuardarMensajeException
import grupo5yomesumo.springboot.serializers.MessageDTO
import grupo5yomesumo.springboot.service.ChatMessage
import grupo5yomesumo.springboot.service.MessageService

class MessageEventListener(
    val server: SocketIOServer,
    val service: MessageService
): DataListener<MessageDTO> {

    override fun onData(client: SocketIOClient, message: MessageDTO, ack: AckRequest) {
        try {
            println("Received message: ${message!!.texto}")
            service.saveMessage(message)
            server.getRoomOperations(message.eventoId.toString()).sendEvent("chat message", message)
        } catch(e: RuntimeException) {
            throw GuardarMensajeException("Error al guardar mensaje", e)
        }


//        broadcastMessage("chat message", message!!)
    }

    fun broadcastMessage(event: String, data: ChatMessage) {
        server.allClients.forEach { client ->
            client.sendEvent(event, data)
        }
    }

}