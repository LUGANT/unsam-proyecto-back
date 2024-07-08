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

    fun findSolicitudsByEventoId(eventoId: Long) : List<Solicitud>

    fun findSolicitudsBySolicitanteId(solicitanteId: Long) : List<Solicitud>

    @Query("""SELECT s from Solicitud s
        WHERE s.solicitante.id = :solicitanteId
        AND s.estado = 'ACEPTADA'
        AND (s.evento.fecha < CURRENT_DATE OR (s.evento.fecha = CURRENT_DATE AND s.evento.hora < CURRENT_TIME))
    """)
    fun findSolicitudesAceptadasBySolicitanteEventosTerminados(@Param("solicitanteId")solicitanteId: Long) : List<Solicitud>

    @Query("""SELECT s from Solicitud s
        WHERE s.solicitante.id = :solicitanteId
        AND s.estado = :estado
        AND (s.evento.fecha > CURRENT_DATE OR (s.evento.fecha = CURRENT_DATE AND s.evento.hora > CURRENT_TIME))
    """)
    fun findSolicitudesBySolicitanteAndEstadoEventosFuturos(@Param("solicitanteId")solicitanteId: Long, @Param("estado")estado: Estado) : List<Solicitud>

    @Query("""SELECT s from Solicitud s
        WHERE s.evento.id = :eventoId
        AND s.estado = 'ACEPTADA'
        AND (s.evento.fecha < CURRENT_DATE OR (s.evento.fecha = CURRENT_DATE AND s.evento.hora < CURRENT_TIME))
    """)
    fun findSolicitudesAceptadasByEventoEventosTerminados(@Param("eventoId")eventoId: Long): List<Solicitud>

    @Query("""SELECT s from Solicitud s
        WHERE s.evento.id = :eventoId
        AND s.estado = 'ACEPTADA'
        AND (s.evento.fecha < CURRENT_DATE OR (s.evento.fecha = CURRENT_DATE AND s.evento.hora < CURRENT_TIME))
        AND s.solicitante.id <> :solicitanteId
    """)
    fun findSolicitudesAceptadasByEventoAndUsuarioNotAnfitrionEventosTerminados(@Param("eventoId")eventoId: Long, @Param("solicitanteId")solicitanteId: Long): List<Solicitud>

    fun countSolicitudsByEventoIdAndEstado(eventoId: Long, estado: Estado): Int

    fun existsBySolicitanteIdAndEventoId(solicitanteId: Long, eventoId: Long) : Boolean

    fun existsBySolicitanteAndEventoAndEstado(solicitante: Usuario, evento: Evento, estado: Estado): Boolean

}