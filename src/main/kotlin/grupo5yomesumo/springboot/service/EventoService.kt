package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.Actividadrepository
import org.springframework.stereotype.Service
import grupo5yomesumo.springboot.repository.EventoRepository
import grupo5yomesumo.springboot.repository.UsuarioRepository
import jakarta.transaction.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class EventoService (
    val eventoRepository: EventoRepository,
    val actividadService: ActividadService,
    val usuarioService: UsuarioService
) {

    fun getEvento(eventoId: Long): Evento = eventoRepository.findById(eventoId).orElseThrow { NotFoundException("No se encontro un evento con id $eventoId") }

    fun getAllEventos(): List<Evento> = eventoRepository.findAll().toList()

    fun getEventosByAnfitrion(usuarioId: Long): List<Evento> {
        val anfitrion = usuarioService.getUsuario(usuarioId)
        return eventoRepository.findEventosByAnfitrion(anfitrion)
    }

    fun getEventoFilter(actividadNombre: String): List<Evento> {
        val actividad : Actividad = actividadService.getActividadBynombre(actividadNombre)
        val eventos : List<Evento> = eventoRepository.findEventosByActividad(actividad)
        return eventos
    }

    @Transactional
    fun crearEvento(anfitrionId: Long, actividadId: Long, fechaUnparsed: String, direccion: String, capacidadMaxima : Int){
        val anfitrion: Usuario = usuarioService.getUsuario(anfitrionId)
        val actividad : Actividad = actividadService.getActividad(actividadId)

        val dateFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fecha = LocalDate.parse(fechaUnparsed, dateFormater)

        val nuevoEvento = Evento(anfitrion = anfitrion, actividad = actividad, fecha = fecha, direccion = direccion, capacidadMaxima = capacidadMaxima)
        eventoRepository.save(nuevoEvento)
    }

    @Transactional
    fun eliminarEvento(eventoId : Long) {
        val evento = getEvento(eventoId)
        eventoRepository.delete(evento)
    }

}