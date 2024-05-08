package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventoRepository: CrudRepository<Evento, Long> {

    fun findEventosByActividad(actividad: Actividad) : List<Evento>

}

