package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Mensaje
import java.time.LocalDate
import java.time.LocalTime

class MessageDTO(
    val perfilURL: String,
    val username: String,
    val eventoId: Long,
    val userId: Long,
    val fecha: String = "",
    val hora: String = "",
    val texto: String = ""
){
    fun fromDTOtoDomain(): Mensaje{
        val mensaje = Mensaje(
            evento = eventoId,
            usuario = userId,
            fecha = LocalDate.now(),
            horario = LocalTime.now(),
            texto = texto,
            username = username
        )
        return mensaje
    }

}