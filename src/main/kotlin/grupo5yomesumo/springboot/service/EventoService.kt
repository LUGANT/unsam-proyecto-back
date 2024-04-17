package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Evento
import org.springframework.stereotype.Service
import grupo5yomesumo.springboot.repository.EventoRepository
import grupo5yomesumo.springboot.repository.UsuarioRepository

@Service
class EventoService (
    val eventoRepository: EventoRepository,
    val usuarioRepository: UsuarioRepository
) {

    fun getAllEventos(): List<Evento> = eventoRepository.findAll()
    fun getEvento(eventoId: Long): Evento = eventoRepository.getById(eventoId)
    fun aceptarSolicitud(eventoId: Long, usuarioId: Long) {
        val evento = eventoRepository.getById(eventoId)
        val invitado = usuarioRepository.getById(eventoId)

        evento.responderSolicitud(invitado, true)
    }

    fun rechazarSolicitud(eventoId: Long, usuarioId: Long) {
        val evento = eventoRepository.getById(eventoId)
        val invitado = usuarioRepository.getById(eventoId)

        evento.responderSolicitud(invitado, false)
    }

    fun enviarSolicitud(eventoId: Long, usuarioId: Long) {
        val evento = eventoRepository.getById(eventoId)
        val invitado = usuarioRepository.getById(eventoId)

        evento.agregarSolicitud(invitado)
    }

}