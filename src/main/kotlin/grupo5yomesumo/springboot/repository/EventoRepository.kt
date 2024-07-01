package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface EventoRepository: CrudRepository<Evento, Long> {

    fun findEventosByActividad(actividad: Actividad) : List<Evento>

//    @Query("SELECT e FROM Evento e WHERE e.actividad.id = :actividadId AND NOT e.anfitrion.id = :usuarioId ")
//    fun findEventosByActividad(@Param("actividadId") actividadId: Long, @Param("usuarioId") usuarioId: Long): List<Evento>

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
        """)
    fun findEventosHome(@Param("id") usuarioId: Long): List<Evento>

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
            AND e.actividad IN :actividades
        """)
    fun findEventosHomeFilter(@Param("id")usuarioId: Long, @Param("actividades")actividades: List<Actividad>): List<Evento>

    fun findEventosByAnfitrion(antifrion: Usuario): List<Evento>

    fun findEventosByAnfitrionAndFechaBefore(anfitrion: Usuario, fecha: LocalDate,) : List<Evento>

    fun findEventosByAnfitrionAndFechaAfter(anfitrion: Usuario, fecha: LocalDate) : List<Evento>

    fun existsByAnfitrionAndId(anfitrion: Usuario, eventoId: Long) : Boolean

    @Query("SELECT e FROM Evento e WHERE NOT e.anfitrion.id = :id")
    fun findEventosNotAnfitrionId(@Param("id") usuarioId: Long): List<Evento>

//    @Query("""SELECT e FROM Evento e
//        WHERE (SELECT COUNT(s) FROM Solicitud s WHERE s.evento = e AND s.estado = 'ACEPTADA') < e.capacidadMaxima
//        AND NOT e.anfitrion.id = :id
//        """)
//    fun findEventosHome(@Param("id") usuarioId: Long): List<Evento>

    fun findEventosByFechaGreaterThanEqual(fecha: LocalDate) : List<Evento>

}

