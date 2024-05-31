package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Estado
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.SolicitudRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SolicitudService(
    val solicitudRepository: SolicitudRepository,
    val eventoService: EventoService,
    val usuarioService: UsuarioService,
) {

    fun getSolicitud(solicitudId: Long): Solicitud = solicitudRepository.findById(solicitudId).orElseThrow { NotFoundException("No se encontro la solicitud del id $solicitudId") }

    fun getSolicitudesByEvento(eventoId: Long) : List<Solicitud>{
        val evento = eventoService.getEvento(eventoId)
        return solicitudRepository.findSolicitudsByEvento(evento)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun responder(solicitudId: Long, aceptada: Boolean){
        val solicitud = getSolicitud(solicitudId)
        solicitud.responderSolicitud(aceptada)
        solicitudRepository.save(solicitud)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun crear(eventoId: Long, solicitanteId: Long){
        val evento = eventoService.getEvento(eventoId)
        val solicitante = usuarioService.getUsuario(solicitanteId)
        val nuevaSolicitud = Solicitud(evento = evento, solicitante = solicitante)
        solicitudRepository.save(nuevaSolicitud)
    }

    fun solicitudesDeUsuario(usuarioId: Long) : List<Evento>{
        val usuario = usuarioService.getUsuario(usuarioId)

        val eventos = solicitudRepository.findSolicitudsBySolicitante(usuario).map { solicitud -> eventoService.getEvento(solicitud.evento.id)}
        return eventos
    }

   fun solicitudesAceptadasDeEvento(eventoId: Long): List<Solicitud>{
       return solicitudRepository.findSolicitudesAceptadasByEvento(eventoId)
   }

}