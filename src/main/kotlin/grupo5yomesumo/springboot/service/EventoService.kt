package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.controller.UbicacionProps
import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Ubicacion
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.*
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional
import org.springframework.data.geo.Point
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class EventoService (
    val eventoRepository: EventoRepository,
    val actividadService: ActividadService,
    val usuarioService: UsuarioService,
    val ubicacionRepository: UbicacionRepository
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

    fun getEventosTerminadosByAnfitrion(anfitrionId: Long) : List<Evento> {
        val anfitrion = usuarioService.getUsuario(anfitrionId)
        return eventoRepository.findEventosByAnfitrionAndFechaBefore(anfitrion, fecha = LocalDate.now())
    }


    @Transactional
    fun crearEvento(anfitrionId: Long, actividadId: Long, fechaUnparsed: String, horaUnparsed: String, descripcion: String, ubicacionProps: UbicacionProps, capacidadMaxima : Int){
        val anfitrion: Usuario = usuarioService.getUsuario(anfitrionId)
        val actividad : Actividad = actividadService.getActividad(actividadId)

        val formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fecha = LocalDate.parse(fechaUnparsed, formatterDate)

        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val hora = LocalTime.parse(horaUnparsed, formatterTime)

        //UBICACION
        val nombreLugar = ubicacionProps.nombreCompletoLugar
        val barrio = ubicacionProps.barrio
        val coordenadas = Point(ubicacionProps.lat.toDouble(), ubicacionProps.lon.toDouble())
        val ubicacion = Ubicacion(nombreLugar = nombreLugar, barrio = barrio, coordenadas = coordenadas)
        ubicacionRepository.save(ubicacion)


        val nuevoEvento = Evento(anfitrion = anfitrion, actividad = actividad, descripcion = descripcion , fecha = fecha, hora = hora, ubicacion = ubicacion, capacidadMaxima = capacidadMaxima)
        eventoRepository.save(nuevoEvento)
    }

    @Transactional
    fun eliminarEvento(eventoId : Long) {
        val evento = getEvento(eventoId)
        eventoRepository.delete(evento)
    }

    fun eventoEsDeAnfitrion(evento: Evento, usuario: Usuario) = eventoRepository.existsByAnfitrionAndId(usuario, evento.id)


}