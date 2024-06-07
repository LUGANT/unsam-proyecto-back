package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.domain.Ubicacion

class EventoDetalladoDTO(
    val id: Long,
    val anfitrion: UsuarioMinDTO,
    val actividad: Actividad,
    val fecha: String,
    val ubicacion: Ubicacion,
    val capacidadMaxima: Int,
    val descripcion: String,
    val participantes: List<ParticipanteDTO>
) {
    constructor(evento: Evento, solicitudesAceptadas: List<Solicitud>):this(
        id = evento.id,
        anfitrion = UsuarioMinDTO(evento.anfitrion),
        actividad = evento.actividad,
        fecha = "${evento.fecha.year}-${evento.fecha.month}-${evento.fecha.dayOfMonth}",
        ubicacion = evento.ubicacion,
        capacidadMaxima = evento.capacidadMaxima,
        descripcion = evento.descripcion,
        participantes = solicitudesAceptadas.map { ParticipanteDTO(it.solicitante) }
    )
}