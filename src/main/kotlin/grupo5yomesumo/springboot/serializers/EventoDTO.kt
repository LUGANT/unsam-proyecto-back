package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Evento
import java.time.LocalDate

class EventoDTO(
    val id: Long,
    val anfitrion: String/*Usuario*/,
    val actividad: String/*Actividad*/,
    val fecha: LocalDate,
    val direccion: String,
    val solicitudes: List<String>/*List<Usuario>*/,
    val aceptados: List<String>/*List<Usuario>*/
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
