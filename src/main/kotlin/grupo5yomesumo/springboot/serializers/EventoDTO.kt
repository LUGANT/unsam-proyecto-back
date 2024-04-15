package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Evento
import java.time.LocalDate

class EventoDTO(
    val id: Long,
    val anfitrion: String /*Usuario*/ = "",
    val actividad: String /*Actividad*/ = "",
    val fecha: LocalDate = LocalDate.now(),
    val direccion: String = "",
    val solicitudes: List<String> /*MutableList<Usuario>*/ = mutableListOf(),
    val aceptados: List<String> /*MutableList<Usuario>*/ = mutableListOf(),
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
