package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Estado
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import java.time.LocalDate

interface EventoPagedRepository: PagingAndSortingRepository<Evento,Long> {

    fun findEventosByAnfitrion(antifrion: Usuario, pageable: Pageable): List<Evento>

    fun findEventosByActividad(actividad: Actividad, pageable: Pageable): List<Evento>

    fun findEventosByAnfitrionAndFechaBefore(anfitrion: Usuario, fecha: LocalDate?, pageable: Pageable): List<Evento>

}