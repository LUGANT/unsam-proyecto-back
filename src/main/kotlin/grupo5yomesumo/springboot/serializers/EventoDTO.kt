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
    val direccion: String,
    val solicitudes: List<Usuario>/*List<Usuario>*/,
    val aceptados: List<Usuario>/*List<Usuario>*/
) {

    constructor(evento: Evento): this (
        id = evento.id,
        anfitrion = evento.anfitrion,
        actividad = evento.actividad,
        fecha = evento.fecha,
        direccion = evento.direccion,
        solicitudes = evento.solicitudes,
        aceptados = evento.aceptados
    )

}
