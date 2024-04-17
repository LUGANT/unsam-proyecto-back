package grupo5yomesumo.springboot

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.repository.EventoRepository
import grupo5yomesumo.springboot.repository.UsuarioRepository
import grupo5yomesumo.springboot.service.EventoService
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class Bootstrap(
    val eventoService: EventoService,
    val eventoRepository: EventoRepository,
    val usuarioRepository: UsuarioRepository
) : InitializingBean
{
    override fun afterPropertiesSet() {
        //EVENTOS

        var usuarioA = Usuario(nombre = "Felipe", apellido = "Gamalleri", username = "feli", password = "1234")
        var usuarioB = Usuario(nombre = "Nicolas", apellido = "Masuyama", username = "masu", password = "1234")
        var usuarioC = Usuario(nombre = "Lucas", apellido = "Antenni", username = "lugant", password = "1234")
        var usuarioD = Usuario(nombre = "Rodrigo", apellido = "Tartaglia", username = "rodri", password = "1234")

        arrayOf(
            usuarioA, usuarioB
        ).forEach { usuarioRepository.save(it) }

        val futbolConLosPibes = Evento(anfitrion = usuarioA, actividad = "Futbol", fecha = LocalDate.now(), direccion = "calle trucha 123", solicitudes = mutableListOf(usuarioC), aceptados = mutableListOf(usuarioD))
        val basquet3Vs3 = Evento(anfitrion = usuarioB, actividad = "Basquet", fecha = LocalDate.now(), direccion = "calle verdadera 321", solicitudes = mutableListOf(usuarioD), aceptados = mutableListOf(usuarioC))

        arrayOf(
            futbolConLosPibes, basquet3Vs3
        ).forEach { eventoRepository.save(it) }
    }
}
