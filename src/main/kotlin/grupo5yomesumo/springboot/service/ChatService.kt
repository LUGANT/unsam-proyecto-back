package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.repository.MensajeRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    val mensajeRepository: MensajeRepository,
) {

    fun getChatByEvento(eventoId : Long) : List<Mensaje> = mensajeRepository.getMensajesByEvento(eventoId)

    @Transactional
    fun nuevoMensaje(eventoId: Long, usuarioId: Long, texto: String) {
        val mensajeNuevo = Mensaje(
            evento = eventoId,
            usuario = usuarioId,
            horario = LocalDateTime.now(),
            texto = texto
        )
        mensajeRepository.save(mensajeNuevo)
    }

}