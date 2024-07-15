package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalTime

@Repository
interface EventoRepository: CrudRepository<Evento, Long> {

    @Query("""SELECT e FROM Evento e 
            WHERE NOT EXISTS (
                SELECT s FROM Solicitud s
                WHERE s.evento.id = e.id
                AND s.solicitante.id = :id
                AND s.estado = 'ACEPTADA'
            ) AND (
                SELECT COUNT(s) FROM Solicitud s 
                WHERE s.evento.id = e.id 
                AND s.estado = 'ACEPTADA'  
            ) < e.capacidadMaxima
            AND NOT e.anfitrion.id = :id
            AND (e.fecha > CURRENT_DATE OR (e.fecha = CURRENT_DATE AND e.hora > CURRENT_TIME))
        """)
    fun findEventosHome(@Param("id") usuarioId: Long, pageable: Pageable): Page<Evento>

    @Query("""SELECT e FROM Evento e 
            WHERE NOT EXISTS (
                SELECT s FROM Solicitud s
                WHERE s.evento.id = e.id
                AND s.solicitante.id = :id
                AND s.estado = 'ACEPTADA'
            ) AND (
                SELECT COUNT(s) FROM Solicitud s 
                WHERE s.evento.id = e.id 
                AND s.estado = 'ACEPTADA'  
            ) < e.capacidadMaxima
            AND NOT e.anfitrion.id = :id
            AND (e.fecha > CURRENT_DATE OR (e.fecha = CURRENT_DATE AND e.hora > CURRENT_TIME))
            AND e.actividad IN :actividades
        """)
    fun findEventosHomeFilter(@Param("id")usuarioId: Long, @Param("actividades")actividades: List<Actividad>, pageable: Pageable): Page<Evento>

    @Query("""SELECT e FROM Evento e
            WHERE e.anfitrion.id = :id
            AND (e.fecha < CURRENT_DATE OR (e.fecha = CURRENT_DATE AND e.hora < CURRENT_TIME))
    """)
    fun findEventosTerminadosByAnfitrion(@Param("id")anfitrionId: Long) : List<Evento>

    @Query("""SELECT e FROM Evento e
            WHERE e.anfitrion.id = :id
            AND (e.fecha > CURRENT_DATE OR (e.fecha = CURRENT_DATE AND e.hora > CURRENT_TIME))
    """)
    fun findEventosActivosByAnfitrion(@Param("id")anfitrionId: Long) : List<Evento>

    @Query("""SELECT e FROM Evento e
            WHERE (e.fecha > CURRENT_DATE OR (e.fecha = CURRENT_DATE AND e.hora > CURRENT_TIME))
    """)
    fun findAllEventosActivos() : List<Evento>

    fun existsByAnfitrionIdAndId(anfitrionId: Long, eventoId: Long) : Boolean

}

