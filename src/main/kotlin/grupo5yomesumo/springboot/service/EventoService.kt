package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Evento
import org.springframework.stereotype.Service
import grupo5yomesumo.springboot.repository.EventoRepository

@Service
class EventoService (
    val eventoRepository: EventoRepository
) {

    fun getAllEventos(): List<Evento> = eventoRepository.findAll()

}