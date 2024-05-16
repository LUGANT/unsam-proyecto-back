package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventoRepository: CrudRepository<Evento, Long> {

    fun findEventosByActividad(actividad: Actividad) : List<Evento>

    fun findEventosByAnfitrion(antifrion: Usuario): List<Evento>

}

