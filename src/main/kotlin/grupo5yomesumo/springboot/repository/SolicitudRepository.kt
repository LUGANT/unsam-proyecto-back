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

    @Query("select s from Solicitud s WHERE s.evento.id = :eventoId")
    fun findSolicitudesAceptadasByEvento(@Param("eventoId") eventoId: Long): List<Solicitud>

    fun findSolicitudsBySolicitante(solicitante : Usuario) : List<Solicitud>

    fun findSolicitudsByEvento(evento: Evento) : List<Solicitud>

    fun findSolicitudsBySolicitanteAndEstadoAndEvento_FechaBefore(solicitante: Usuario, estado: Estado, fecha: LocalDate) : List<Solicitud>


}