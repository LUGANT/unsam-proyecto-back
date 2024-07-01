package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.springframework.data.geo.Point
import org.springframework.data.mongodb.core.mapping.Document

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
class Evento(
    @Id @GeneratedValue var id: Long = 0,
    @ManyToOne var anfitrion: Usuario = Usuario(),
    @ManyToOne var actividad: Actividad = Actividad(),
    @Column var fecha: LocalDate = LocalDate.now(),
    @Column var hora: LocalTime = LocalTime.now(),
    @ManyToOne val ubicacion: Ubicacion = Ubicacion(),
    @Column val capacidadMaxima: Int = 0,
    @Column val descripcion : String = ""
){

    fun activo(): Boolean {
        val eventoDateTime = LocalDateTime.of(fecha, hora)
        return eventoDateTime.isAfter(LocalDateTime.now())
    }

    fun estaLleno(cantidadParticipantes: Int) = cantidadParticipantes >= capacidadMaxima

}

@Entity
data class Ubicacion(
    @Id @GeneratedValue var id: Long = 0,
    @Column val nombreLugar: String = "",
    @Column val barrio: String = "",
    @Column val coordenadas: Point = Point(0.0, 0.0)
)