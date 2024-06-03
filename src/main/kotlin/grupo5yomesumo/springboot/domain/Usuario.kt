package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.serializers.UsuarioDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = 0,
    @Column(length = 40)val nombre: String = "",
    @Column(length = 40)val apellido: String = "",
    @Column(length = 40)val username: String = "",
    @Column var password: String = "",
    @Column var puntuacion : Double = 0.0
) {

    fun crearEvento(actividad: Actividad, fecha: LocalDate, direccion: String, capacidadMaxima : Int): Evento{
        return Evento(anfitrion = this, actividad = actividad, fecha = fecha, direccion = direccion, capacidadMaxima = capacidadMaxima)
    }

}