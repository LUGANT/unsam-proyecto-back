package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.Actividadrepository
import org.springframework.stereotype.Service
import grupo5yomesumo.springboot.repository.EventoRepository
import grupo5yomesumo.springboot.repository.UsuarioRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class EventoService (
    val eventoRepository: EventoRepository,
    val usuarioRepository: UsuarioRepository,
    val actividadRepository: Actividadrepository
) {

    fun getEventoFilter(eventoId: Long, actividadId: Long): List<Evento> {
        val actividad : Actividad = actividadRepository.findById(actividadId).orElseThrow { NotFoundException("No se encontro una actividad con el id $actividadId") }
        val eventos : List<Evento> = eventoRepository.findEventosByActividad(actividad)
        return eventos
    }

    fun crearEvento(anfitrionId: Long, actividadId: Long, fechaUnparsed: String, direccion: String, capacidadMaxima : Int){
        val anfitrion: Usuario = usuarioRepository.findById(anfitrionId).orElseThrow { NotFoundException("No se encontro el usuario con el id $anfitrionId") }
        val actividad : Actividad = actividadRepository.findById(actividadId).orElseThrow { NotFoundException("No se encontro una actividad con el id $actividadId") }

        val dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fecha = LocalDate.parse(fechaUnparsed, dateFormater)

        val nuevoEvento = Evento(anfitrion = anfitrion, actividad = actividad, fecha = fecha, direccion = direccion, capacidadMaxima = capacidadMaxima)
        eventoRepository.save(nuevoEvento)
    }

    fun getAllEventos(): List<Evento> = eventoRepository.findAll().toList()

    fun getEvento(eventoId: Long): Evento = eventoRepository.findById(eventoId).orElseThrow { NotFoundException("No se encontro un evento con id $eventoId") }

//    fun aceptarSolicitud(eventoId: Long, usuarioId: Long) {
//        val evento = eventoRepository.getById(eventoId)
//        val invitado = usuarioRepository.findById(eventoId)
//
//        evento.responderSolicitud(invitado, true)
//    }
//
//    fun rechazarSolicitud(eventoId: Long, usuarioId: Long) {
//        val evento = eventoRepository.getById(eventoId)
//        val invitado = usuarioRepository.getById(eventoId)
//
//        evento.responderSolicitud(invitado, false)
//    }
//
//    fun enviarSolicitud(eventoId: Long, usuarioId: Long) {
//        val evento = eventoRepository.getById(eventoId)
//        val invitado = usuarioRepository.getById(eventoId)
//
//        evento.agregarSolicitud(invitado)
//    }

}