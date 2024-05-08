package grupo5yomesumo.springboot

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.repository.Actividadrepository
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
    val usuarioRepository: UsuarioRepository,
    val actividadRepository: Actividadrepository
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


        //ACTIVIDADES
        val basquet = Actividad(nombre = "Basquet", esGrupal = true)
        val futbol = Actividad(nombre = "Futbol", esGrupal = true)

        arrayOf(
            basquet, futbol
        ).forEach { actividadRepository.save(it) }


        val futbolConLosPibes = Evento(anfitrion = usuarioA, actividad = futbol, fecha = LocalDate.now(), direccion = "calle trucha 123")
        val basquet3Vs3 = Evento(anfitrion = usuarioB, actividad = basquet, fecha = LocalDate.now(), direccion = "calle verdadera 321")

        arrayOf(
            futbolConLosPibes, basquet3Vs3
        ).forEach { eventoRepository.save(it) }
    }
}
