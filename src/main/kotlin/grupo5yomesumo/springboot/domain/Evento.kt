package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Evento(
    @Id @GeneratedValue var id: Long = 0,
    @ManyToOne var anfitrion: Usuario = Usuario(),
    @ManyToOne var actividad: Actividad = Actividad(),
    @Column var fecha: LocalDate = LocalDate.now(),
    @Column var direccion: String = "",
    @Column val capacidadMaxima: Int = 0,
    @Column val descripcion : String = ""
){

    fun activo(): Boolean = fecha.isAfter(LocalDate.now())

}
