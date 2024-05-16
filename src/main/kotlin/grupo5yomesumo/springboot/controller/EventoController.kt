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

    @GetMapping("")
    @Operation(summary = "Devuelve eventos con filtro")
    fun getEventoFilter(@RequestParam(value = "actividad") actividadId : Long) : List<Evento> = eventoService.getEventoFilter(actividadId)

    @GetMapping("{eventoId}")
    @Operation(summary = "Devuelve evento por id")
    fun getEvento(@PathVariable eventoId: Long) : Evento = eventoService.getEvento(eventoId)

    @GetMapping("usuario/{usuarioId}")
    @Operation(summary = "Devuelve los eventos de un usuario específico.")
    fun getEventosUsuario(@PathVariable usuarioId: Long): List<Evento> = eventoService.getEventosByAnfitrion(usuarioId)

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