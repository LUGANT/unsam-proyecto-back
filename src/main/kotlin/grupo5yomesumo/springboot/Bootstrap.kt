package grupo5yomesumo.springboot

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.repository.EventoRepository
import grupo5yomesumo.springboot.service.EventoService
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.time.LocalDate


@Component
class Bootstrap(
    val eventoService: EventoService,
    val eventoRepository: EventoRepository
) : InitializingBean
{
    override fun afterPropertiesSet() {
        //EVENTOS
        val futbolConLosPibes = Evento(anfitrion = "feli", actividad = "Futbol", fecha = LocalDate.now(), direccion = "calle trucha 123", solicitudes = mutableListOf("asd", "dsa"), aceptados = mutableListOf("jkl", "lkj"))
        val basquet3Vs3 = Evento(anfitrion = "masu", actividad = "Basquet", fecha = LocalDate.now(), direccion = "calle verdadera 321", solicitudes = mutableListOf("asd", "dsa"), aceptados = mutableListOf("jkl", "lkj"))

        val savedUsers = arrayOf(
            futbolConLosPibes, basquet3Vs3
        ).map { eventoRepository.save(it) }
    }
}
