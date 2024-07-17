package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.repository.MensajeRepository
import grupo5yomesumo.springboot.serializers.MessageDTO
import org.springframework.stereotype.Service

@Service
class MessageService(
    val mensajeRepository: MensajeRepository,
) {

//    fun getHistorial(eventoId : Long) : List<Mensaje> = mensajeRepository.getMensajesByEventoOrderByFechaAsc(eventoId)

    fun getMessages(eventoId: Long): List<Mensaje> = mensajeRepository.getMensajeByEventoId(eventoId)

    fun saveMessage(messageDTO: MessageDTO){
        val mensaje = fromDTOtoDomain(messageDTO)
        mensajeRepository.save(mensaje)
    }

    fun saveMessage(mensaje: Mensaje){
        mensajeRepository.save(mensaje)
    }

    fun fromDTOtoDomain(message: MessageDTO): Mensaje{
        val mensaje = Mensaje(
            eventoId = message.eventoId,
            usuarioId = message.usuarioId,
            texto = message.texto,
            username = message.username,
            fechaHora = message.fechaHora
        )
        return mensaje
    }

    fun fromDomainToDTO(mensaje: Mensaje): MessageDTO {
        return MessageDTO(
            usuarioId = mensaje.usuarioId,
            eventoId = mensaje.eventoId,
            texto = mensaje.texto,
            username = mensaje.username,
            fechaHora = mensaje.fechaHora,
        )
    }

}