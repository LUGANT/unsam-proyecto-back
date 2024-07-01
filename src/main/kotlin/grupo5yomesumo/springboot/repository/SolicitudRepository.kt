package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Estado
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.EnumSet


@Repository
interface SolicitudRepository : CrudRepository<Solicitud, Long> {

    @Query("SELECT s FROM Solicitud s WHERE s.evento.anfitrion.id = :usuarioId")
    fun findSolicitudesByAnfitrion(@Param("usuarioId") usuarioId: Long)

    fun findSolicitudsByEventoIdAndEstado(eventoId: Long, estado: Estado): List<Solicitud>

    fun findSolicitudsByEvento(evento: Evento) : List<Solicitud>

    fun findSolicitudsBySolicitante(solicitante : Usuario) : List<Solicitud>

    fun findSolicitudsBySolicitanteAndEstadoAndEvento_FechaLessThanEqual(solicitante: Usuario, estado: Estado, fecha: LocalDate) : List<Solicitud>

    fun existsBySolicitanteAndEvento(solicitante: Usuario, evento : Evento) : Boolean

    fun countSolicitudsByEventoIdAndEstado(eventoId: Long, estado: Estado): Int

    fun findSolicitudsBySolicitanteAndEstadoAndEvento_FechaAfter(solicitante: Usuario, estado: Estado, fecha: LocalDate) : List<Solicitud>

    fun findSolicitudsByEventoAndEstadoAndEventoFechaBefore(evento: Evento, estado: Estado, fecha: LocalDate): List<Solicitud>

    fun findSolicitudsByEventoAndEstadoAndEventoFechaBeforeAndSolicitanteIsNot(evento: Evento, estado: Estado, fecha: LocalDate, solicitante: Usuario): List<Solicitud>

    fun existsBySolicitanteAndEventoAndEstado(solicitante: Usuario, evento: Evento, estado: Estado): Boolean

//    @Query("SELECT s FROM Solicitud s WHERE s.evento = :evento AND s.estado = :estado AND s.evento.fecha < :fecha AND s.solicitante NOT IN (SELECT o.usuarioOpinado FROM Opinion o WHERE o.usuarioOpinante = :usuario)")
//    fun findUsuariosParaOpinarPorAnfitrion(evento: Evento, estado: Estado, fecha: LocalDate, usuario: Usuario): List<Solicitud>
//
//    @Query("SELECT s FROM Solicitud s WHERE s.evento = :evento AND s.estado = :estado AND s.evento.fecha < :fecha AND s.solicitante <> :usuario AND s.solicitante NOT IN (SELECT o.usuarioOpinado FROM Opinion o WHERE o.usuarioOpinante = :usuario)")
//    fun findUsuariosParaOpinarPorParticipante(evento: Evento, estado: Estado, fecha: LocalDate, usuario: Usuario): List<Solicitud>

}