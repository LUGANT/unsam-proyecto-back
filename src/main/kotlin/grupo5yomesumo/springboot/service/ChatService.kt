package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.repository.MensajeRepository
import org.springframework.stereotype.Service

@Service
class ChatService(
    val mensajeRepository: MensajeRepository
) {

    fun getChatByEvento(eventoId : Long) : List<Mensaje> = mensajeRepository.getMensajesByEvento(eventoId)

}