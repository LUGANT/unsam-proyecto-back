package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.domain.Usuario

class SolicitudDTO(
    val id : Long,
    val usuario : Usuario,
    val puntajeUsuario : Double,
    val evento : Evento
) {
    constructor(solicitud: Solicitud) : this(
        id = solicitud.id,
        usuario = solicitud.solicitante,
        puntajeUsuario = solicitud.solicitante.puntuacion,
        evento = solicitud.evento
    )
}