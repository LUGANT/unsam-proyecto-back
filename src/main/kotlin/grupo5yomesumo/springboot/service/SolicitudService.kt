package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Estado
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.SolicitudRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SolicitudService(
    val solicitudRepository: SolicitudRepository,
    val eventoService: EventoService,
    val usuarioService: UsuarioService,
) {

    fun getSolicitud(solicitudId: Long): Solicitud = solicitudRepository.findById(solicitudId).orElseThrow { NotFoundException("No se encontro la solicitud del id $solicitudId") }

    fun getSolicitudesByEvento(eventoId: Long) : List<Solicitud>{
        val evento = eventoService.getEvento(eventoId)
        return solicitudRepository.findSolicitudsByEventoAndEstado(evento, Estado.PENDIENTE)
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

   fun solicitudesAceptadasDeEvento(eventoId: Long): List<Solicitud> {
       val solicitud = solicitudRepository.findSolicitudesAceptadasByEvento(eventoId)
       return solicitud.filter { it.estado == Estado.ACEPTADA }
       // Por ahora es así, pero hay que ver de hacer la consulta en la base de datos
   }

    fun getEventosAsistidosPor(usuarioId: Long) :List<Evento> {
        val usuario = usuarioService.getUsuario(usuarioId)
        return solicitudRepository.findSolicitudsBySolicitanteAndEstadoAndEvento_FechaBefore(usuario, estado = Estado.ACEPTADA, fecha = LocalDate.now()).map { eventoService.getEvento(it.id) }
    }

    fun habilitadaSolicitud(usuarioId : Long, eventoId: Long) : Boolean{
        val usuario = usuarioService.getUsuario(usuarioId)
        val evento = eventoService.getEvento(eventoId)
        return !solicitudRepository.existsBySolicitanteAndEvento(usuario, evento) && !eventoService.eventoEsDeAnfitrion(evento, usuario)
    }

    fun solicitudesPendientesDeEvento(eventoId: Long): Int{
        return solicitudRepository.countSolicitudesPendientes(eventoId)
    }

}