package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Chat
import grupo5yomesumo.springboot.domain.Mensaje
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : MongoRepository<Chat, String> {

//    fun getChatByEvento(evento: Long) : Chat

    fun findByEventoId(eventoId: Long): Chat?
    fun findByUserIds(userIds: List<Long>): Chat?
    fun findByEventoIdOrUserIds(eventoId: Long, userIds: List<Long>): Chat?

}

@Repository
interface MensajeRepository : MongoRepository<Mensaje, String> {

//    fun getMensajesByEvento(evento: Long) : List<Mensaje>



}