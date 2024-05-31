package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.serializers.EventoDTO
import grupo5yomesumo.springboot.serializers.EventoDetalladoDTO
import grupo5yomesumo.springboot.serializers.SolicitudDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import grupo5yomesumo.springboot.service.EventoService
import grupo5yomesumo.springboot.service.SolicitudService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam


@RestController()
@RequestMapping("evento")
@CrossOrigin("*")
class EventoController(
    val eventoService: EventoService,
    val solicitudService: SolicitudService
) {

    @GetMapping("all")
    @Operation(summary = "Devuelve todos los eventos")
    fun getAllEventos(): List<EventoDTO> = eventoService.getAllEventos().map{ EventoDTO(it) }

    @GetMapping("")
    @Operation(summary = "Devuelve eventos con filtro")
    fun getEventoFilter(@RequestParam(value = "actividad") actividadId : Long) : List<EventoDTO> = eventoService.getEventoFilter(actividadId).map { EventoDTO(it) }

    @GetMapping("{eventoId}")
    @Operation(summary = "Devuelve evento por id")
    fun getEvento(@PathVariable eventoId: Long) : EventoDetalladoDTO {
        val evento = eventoService.getEvento(eventoId)
        val participantes =  solicitudService.solicitudesAceptadasDeEvento(eventoId)
        return EventoDetalladoDTO(evento, participantes)
    }

    @GetMapping("usuario/{usuarioId}")
    @Operation(summary = "Devuelve los eventos de un usuario específico.")
    fun getEventosUsuario(@PathVariable usuarioId: Long): List<EventoDTO> = eventoService.getEventosByAnfitrion(usuarioId).map { EventoDTO(it) }

    @PostMapping("crear")
    @Operation(summary = "Crea un evento")
    fun crearEvento(@RequestBody eventoProps: CrearEventProps) = eventoService.crearEvento(
        eventoProps.anfitrionId,
        eventoProps.actividadId,
        eventoProps.fecha,
        eventoProps.direccion,
        eventoProps.capacidadMaxima
    )

    @GetMapping("{eventoId}/solicitudes")
    @Operation(summary = "Devuelve las solicitudes de un evento")
    fun getSolicitudesDeEvento(@PathVariable eventoId: Long) : List<SolicitudDTO> = solicitudService.getSolicitudesByEvento(eventoId).map { SolicitudDTO(it) }

    @DeleteMapping("{eventoId}/borrar")
    @Operation(summary = "Elimina un evento")
    fun eliminarEvento(@PathVariable eventoId: Long) = eventoService.eliminarEvento(eventoId)

}

data class CrearEventProps(
    val anfitrionId: Long,
    val actividadId: Long,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val capacidadMaxima: Int,
)