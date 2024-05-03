package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.Entidad
import grupo5yomesumo.springboot.serializers.UsuarioDTO
import java.time.LocalDate

class Usuario(
    override var id: Long = 0,
    val nombre: String,
    val apellido: String,
    val username: String,
    val password: String,
): Entidad {

    fun crearEvento(actividad: Actividad, fecha: LocalDate, direccion: String): Evento{
        return Evento(anfitrion = this, actividad = actividad, fecha = fecha, direccion = direccion)
    }

}