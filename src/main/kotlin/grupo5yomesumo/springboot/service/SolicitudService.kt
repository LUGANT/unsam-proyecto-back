package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Estado
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.SolicitudRepository
import jakarta.transaction.Transactional
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SolicitudService(
    val solicitudRepository: SolicitudRepository,
    val eventoService: EventoService,
    val usuarioService: UsuarioService,
) {

    fun getSolicitud(solicitudId: Long): Solicitud = solicitudRepository.findById(solicitudId).orElseThrow { NotFoundException("No se encontro la solicitud del id $solicitudId") }

    fun getSolicitudesByEvento(eventoId: Long): List<Solicitud> = solicitudRepository.findSolicitudsByEventoIdAndEstado(eventoId, Estado.PENDIENTE)

    @Transactional(Transactional.TxType.REQUIRED)
    fun responder(solicitudId: Long, aceptada: Boolean){
        val solicitud = getSolicitud(solicitudId)
        if (aceptada) {
        validarCantidadParticipantes(solicitud.evento)
        validarNoParticipa(solicitud.solicitante, solicitud.evento)
        }
        solicitud.responderSolicitud(aceptada)
        solicitudRepository.save(solicitud)
    }

    fun validarNoParticipa(solicitante: Usuario, evento: Evento) {
        if (usuarioParticipante(solicitante, evento)) throw BadRequestException("Este usuario ya se encuentra participando del evento")
    }

    fun usuarioParticipante(solicitante: Usuario, evento: Evento) = solicitudRepository.existsBySolicitanteAndEventoAndEstado(solicitante, evento, Estado.ACEPTADA)

    fun validarCantidadParticipantes(evento: Evento) {
        if (eventoLleno(evento)) throw BadRequestException("No puedes aceptar más solicitudes porque ya se completó la capacidad máxima")
    }

    fun eventoLleno(evento: Evento) = evento.estaLleno(solicitudRepository.countSolicitudsByEventoIdAndEstado(evento.id, Estado.ACEPTADA))

    @Transactional(Transactional.TxType.REQUIRED)
    fun crear(eventoId: Long, solicitanteId: Long){
        val evento = eventoService.getEvento(eventoId)
        val solicitante = usuarioService.getUsuario(solicitanteId)
        val nuevaSolicitud = Solicitud(evento = evento, solicitante = solicitante)
        solicitudRepository.save(nuevaSolicitud)
    }

    fun solicitudesDeUsuario(usuarioId: Long): List<Evento> = solicitudRepository.findSolicitudsBySolicitanteId(usuarioId).map { it.evento }

   fun solicitudesAceptadasDeEvento(eventoId: Long): List<Solicitud> = solicitudRepository.findSolicitudsByEventoIdAndEstado(eventoId, Estado.ACEPTADA)

    fun getEventosAsistidosPor(usuarioId: Long) :List<Evento> {
        val eventosCreadosTerminados = eventoService.getEventosTerminadosByAnfitrion(usuarioId)
        val eventosAsistidos = solicitudRepository.findSolicitudesAceptadasBySolicitanteEventosTerminados(usuarioId).map { it.evento }
        return eventosAsistidos + eventosCreadosTerminados
    }

    fun habilitadaSolicitud(usuarioId : Long, eventoId: Long) : Boolean{
        return !solicitudRepository.existsBySolicitanteIdAndEventoId(usuarioId, eventoId) && !eventoService.eventoEsDeAnfitrion(eventoId, usuarioId)
    }

    fun solicitudesPendientesDeEvento(eventoId: Long): Int{
        return solicitudRepository.countSolicitudsByEventoIdAndEstado(eventoId, Estado.PENDIENTE)
    }

    fun getEventosPorAsistir(usuarioId: Long) : List<Evento> = solicitudRepository.findSolicitudesBySolicitanteAndEstadoEventosFuturos(usuarioId, estado = Estado.ACEPTADA).map { it.evento }.distinct()

    fun getEventosPendientes(usuarioId: Long) : List<Evento> = solicitudRepository.findSolicitudesBySolicitanteAndEstadoEventosFuturos(usuarioId, estado = Estado.PENDIENTE).map { it.evento }.distinct()

    fun getUsuariosParaOpinar(eventoId: Long, usuarioId: Long): List<Usuario> {
        val evento = eventoService.getEvento(eventoId)
        return if(eventoService.eventoEsDeAnfitrion(eventoId, usuarioId)) {
            solicitudRepository.findSolicitudesAceptadasByEventoEventosTerminados(eventoId).map { it.solicitante }
        } else {
            val anfitrionYDemasParticipantes = mutableListOf(evento.anfitrion)
            anfitrionYDemasParticipantes.addAll(solicitudRepository.findSolicitudesAceptadasByEventoAndUsuarioNotAnfitrionEventosTerminados(eventoId, usuarioId).map { it.solicitante })
            anfitrionYDemasParticipantes.distinct()
        }
    }

}