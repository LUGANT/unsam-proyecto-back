package grupo5yomesumo.springboot.listeners

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.listener.DataListener

class LeaveRoomListener: DataListener<String> {

    override fun onData(client: SocketIOClient?, room: String?, ack: AckRequest?) {
        client!!.leaveRoom(room)
        println("Client " + client.sessionId + " left room: " + room)
    }

}