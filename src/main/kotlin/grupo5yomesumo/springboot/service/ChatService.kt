package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.repository.mensajeRepository
import org.springframework.stereotype.Service

@Service
class ChatService(
    val mensajeRepository: mensajeRepository
) {

    fun getChatByEvento(eventoId : Long) : List<Mensaje> = mensajeRepository.getMensajesByEvento(eventoId)

}