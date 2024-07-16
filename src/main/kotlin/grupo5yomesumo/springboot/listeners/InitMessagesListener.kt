package grupo5yomesumo.springboot.listeners

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.listener.DataListener
import grupo5yomesumo.springboot.repository.MensajeRepository
import grupo5yomesumo.springboot.service.MessageService

class InitMessagesListener(val service: MessageService): DataListener<Long> {

    override fun onData(client: SocketIOClient?, eventId: Long?, ack: AckRequest?) {
        val mensajesAnterioresDTO = service.getMessages(eventId!!).map { service.fromDomainToDTO(it) }
        if (mensajesAnterioresDTO.isNotEmpty()) {
            println("Sending initial messages")
            client!!.sendEvent("initial messages", mensajesAnterioresDTO)
        } else {
            println("No messages for event")
        }

    }

}