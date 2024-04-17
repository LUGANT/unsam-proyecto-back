package grupo5yomesumo.springboot.controller

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


@RestController()
@RequestMapping("evento")
@CrossOrigin("*")
class EventoController(
    val eventoService: EventoService
) {

    @GetMapping("all")
    @Operation(summary = "Devuelve todos los eventos")
    fun getAllEventos(): List<EventoDTO> = eventoService.getAllEventos().map{ EventoDTO(it) }

    @PutMapping("{eventoId}/solicitud/aceptar/{usuarioId}")
    @Operation(summary = "acepta la solicitud de un usuario")
    fun aceptarSolicitud(@PathVariable eventoId: Long, @PathVariable usuarioId: Long) = eventoService.aceptarSolicitud(eventoId, usuarioId)

    @PutMapping("{eventoId}/solicitud/enviar/{usuarioId}")
    @Operation(summary = "envia solicitud a un usuario")
    fun enviarSolicitud(@PathVariable eventoId: Long, @PathVariable usuarioId: Long) = eventoService.enviarSolicitud(eventoId, usuarioId)

    @PutMapping("{eventoId}/solicitud/rechazar/{usuarioId}")
    @Operation(summary = "rechazar solicitud a un usuario")
    fun rechazarSolicitud(@PathVariable eventoId: Long, @PathVariable usuarioId: Long) = eventoService.rechazarSolicitud(eventoId, usuarioId)

    @GetMapping("{eventoId}")
    fun getEvento(@PathVariable eventoId: Long) = eventoService.getEvento(eventoId)

}