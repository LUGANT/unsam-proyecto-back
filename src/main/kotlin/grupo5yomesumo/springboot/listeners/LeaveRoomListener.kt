package grupo5yomesumo.springboot.listeners

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.listener.DataListener

class LeaveRoomListener: DataListener<Long> {

    override fun onData(client: SocketIOClient?, eventId: Long?, ack: AckRequest?) {
        client!!.leaveRoom(eventId.toString())
        println("Client " + client.sessionId + " left room: " + eventId)
    }

}