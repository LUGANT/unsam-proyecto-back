package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.repository.MensajeRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    val mensajeRepository: MensajeRepository,
) {

    fun getHistorial(eventoId : Long) : List<Mensaje> = mensajeRepository.getMensajesByEventoOrderByFechaAsc(eventoId)

}