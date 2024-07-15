package grupo5yomesumo.springboot.listeners

import com.corundumstudio.socketio.AckRequest
import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.listener.DataListener

class JoinRoomListener: DataListener<String> {

    override fun onData(client: SocketIOClient?, room: String?, ack: AckRequest?) {
        client!!.joinRoom(room)
        println("Client " + client.sessionId + " joined room: " + room)
    }
}