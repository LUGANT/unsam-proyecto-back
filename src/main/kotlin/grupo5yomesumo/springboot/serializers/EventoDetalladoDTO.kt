package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud

class EventoDetalladoDTO(
    val id: Long,
    val anfitrion: UsuarioDTO,
    val actividad: Actividad,
    val fecha: String,
    val direccion: String,
    val maximoParticipantes: Int,
    val descripcion: String,
    val participantes: List<ParticipanteDTO>
) {
    constructor(evento: Evento, solicitudesAceptadas: List<Solicitud>):this(
        id = evento.id,
        anfitrion = UsuarioDTO(evento.anfitrion),
        actividad = evento.actividad,
        fecha = "${evento.fecha.year}-${evento.fecha.month}-${evento.fecha.dayOfMonth}",
        direccion = evento.direccion,
        maximoParticipantes = evento.capacidadMaxima,
        descripcion = evento.descripcion,
        participantes = solicitudesAceptadas.map { ParticipanteDTO(it.solicitante) }
    )
}