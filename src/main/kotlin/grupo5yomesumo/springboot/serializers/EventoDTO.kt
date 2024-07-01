package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Ubicacion
import grupo5yomesumo.springboot.domain.Usuario
import java.time.LocalDate
import java.time.LocalTime

class EventoDTO(
    val id: Long,
    val anfitrion: UsuarioMinDTO,
    val actividad: Actividad,
    val fecha: LocalDate,
    val ubicacion: Ubicacion,
    val capacidadMaxima: Int,
    val descripcion: String,
    val solicitudes: Int,
    val hora: String
) {

    constructor(evento: Evento): this (
        id = evento.id,
        anfitrion = UsuarioMinDTO(evento.anfitrion),
        actividad = evento.actividad,
        fecha = evento.fecha,
        ubicacion = evento.ubicacion,
        capacidadMaxima = evento.capacidadMaxima,
        descripcion = evento.descripcion,
        solicitudes = 0,
        hora = evento.hora.toString()
    )

    constructor(evento: Evento, solicitudes: Int): this(
        id = evento.id,
        anfitrion = UsuarioMinDTO(evento.anfitrion),
        actividad = evento.actividad,
        fecha = evento.fecha,
        ubicacion = evento.ubicacion,
        capacidadMaxima = evento.capacidadMaxima,
        descripcion = evento.descripcion,
        solicitudes = solicitudes,
        hora = evento.hora.toString()
    )

}

class EventoHomeDTO(
    val id: Long,
    val anfitrion: UsuarioMinDTO,
    val actividad: Actividad,
    val fecha: LocalDate,
    val hora: LocalTime,
    val ubicacion: Ubicacion,
    val capacidadMaxima: Int,
    val descripcion : String,
    val habilitado: Boolean
) {

    constructor(evento: Evento, habilitado: Boolean): this (
        id = evento.id,
        anfitrion = UsuarioMinDTO(evento.anfitrion),
        actividad = evento.actividad,
        fecha = evento.fecha,
        hora = evento.hora,
        ubicacion = evento.ubicacion,
        capacidadMaxima = evento.capacidadMaxima,
        descripcion = evento.descripcion,
        habilitado = habilitado
    )

}


