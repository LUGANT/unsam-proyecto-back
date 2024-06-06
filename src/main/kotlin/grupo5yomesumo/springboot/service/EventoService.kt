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
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
        val actividadLettercase = actividadNombre.substring(0, 1).uppercase() + actividadNombre.substring(1).lowercase()
        val actividad : Actividad = actividadService.getActividadBynombre(actividadLettercase)
        val eventos : List<Evento> = eventoRepository.findEventosByActividad(actividad)
        return eventos
    }

    @Transactional
    fun crearEvento(anfitrionId: Long, actividadId: Long, fechaUnparsed: String, horaUnparsed: String, descripcion: String, direccion: String, capacidadMaxima : Int){
        val anfitrion: Usuario = usuarioService.getUsuario(anfitrionId)
        val actividad : Actividad = actividadService.getActividad(actividadId)

        val formatterDate = DateTimeFormatter.ofPattern("EEE MMM dd yyyy", Locale.ENGLISH)
        val fecha = LocalDate.parse(fechaUnparsed, formatterDate)

        val cleanedHoraString = horaUnparsed.replace(Regex("\\s*\\(.*\\)"), "")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss 'GMT'Z", Locale.ENGLISH)
        val hora = LocalTime.parse(cleanedHoraString, formatterTime)

        val nuevoEvento = Evento(anfitrion = anfitrion, actividad = actividad, descripcion = descripcion , fecha = fecha, hora = hora, direccion = direccion, capacidadMaxima = capacidadMaxima)
        eventoRepository.save(nuevoEvento)
    }

    @Transactional
    fun eliminarEvento(eventoId : Long) {
        val evento = getEvento(eventoId)
        eventoRepository.delete(evento)
    }

}