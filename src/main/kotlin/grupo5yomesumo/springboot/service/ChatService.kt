package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Chat
import grupo5yomesumo.springboot.repository.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatService(
    val chatRepository : ChatRepository
) {

    fun getChatByEvento(eventoId : Long) : Chat = chatRepository.getChatByEvento(eventoId)

}