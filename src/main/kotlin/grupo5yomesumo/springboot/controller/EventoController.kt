package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.serializers.EventoDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import grupo5yomesumo.springboot.service.EventoService
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
    val eventoService: EventoService
) {

    @GetMapping("all")
    @Operation(summary = "Devuelve todos los eventos")
    fun getAllEventos(): List<EventoDTO> = eventoService.getAllEventos().map{ EventoDTO(it) }

//    @PutMapping("{eventoId}/solicitud/aceptar/{usuarioId}")
//    @Operation(summary = "acepta la solicitud de un usuario")
//    fun aceptarSolicitud(@PathVariable eventoId: Long, @PathVariable usuarioId: Long) = eventoService.aceptarSolicitud(eventoId, usuarioId)
//
//    @PutMapping("{eventoId}/solicitud/enviar/{usuarioId}")
//    @Operation(summary = "envia solicitud a un usuario")
//    fun enviarSolicitud(@PathVariable eventoId: Long, @PathVariable usuarioId: Long) = eventoService.enviarSolicitud(eventoId, usuarioId)
//
//    @PutMapping("{eventoId}/solicitud/rechazar/{usuarioId}")
//    @Operation(summary = "rechazar solicitud a un usuario")
//    fun rechazarSolicitud(@PathVariable eventoId: Long, @PathVariable usuarioId: Long) = eventoService.rechazarSolicitud(eventoId, usuarioId)

    @GetMapping("{eventoId}")
    @Operation(summary = "Devuelve evento por id")
    fun getEvento(@PathVariable eventoId: Long) : Evento = eventoService.getEvento(eventoId)

    @GetMapping("{eventoId}")
    @Operation(summary = "Devuelve eventos con filtro")
    fun getEventoFilter(@PathVariable eventoId: Long, @RequestParam(value = "actividad") actividadId : Long) : List<Evento> = eventoService.getEventoFilter(eventoId, actividadId)

    @PostMapping("crear")
    @Operation(summary = "Crea un evento")
    fun crearEvento(@RequestBody eventoProps: CrearEventProps) = eventoService.crearEvento(
        eventoProps.anfitrionId,
        eventoProps.actividadId,
        eventoProps.fecha,
        eventoProps.direccion,
        eventoProps.capacidadMaxima
    )

}

data class CrearEventProps(
    val anfitrionId: Long,
    val actividadId: Long,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val capacidadMaxima: Int,
)