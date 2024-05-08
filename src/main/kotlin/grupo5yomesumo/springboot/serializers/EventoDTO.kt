package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import java.time.LocalDate

class EventoDTO(
    val id: Long,
    val anfitrion: Usuario,
    val actividad: Actividad,
    val fecha: LocalDate,
    val direccion: String
) {

    constructor(evento: Evento): this (
        id = evento.id,
        anfitrion = evento.anfitrion,
        actividad = evento.actividad,
        fecha = evento.fecha,
        direccion = evento.direccion
    )

}
