package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Mensaje
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

//@Repository
//interface ChatRepository : MongoRepository<Chat, String> {
//
//    fun getChatByEvento(evento: Long) : Chat
//
//}

@Repository
interface mensajeRepository : MongoRepository<Mensaje, String> {

    fun getMensajesByEvento(evento: Long) : List<Mensaje>

}