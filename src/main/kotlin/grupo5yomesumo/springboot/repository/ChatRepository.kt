package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Chat
import grupo5yomesumo.springboot.domain.Evento
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : MongoRepository<Chat, String> {

    fun getChatByEvento(evento: Long) : Chat

}