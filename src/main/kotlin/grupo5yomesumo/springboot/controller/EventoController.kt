package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Solicitud
import grupo5yomesumo.springboot.domain.Ubicacion
import grupo5yomesumo.springboot.serializers.EventoDTO
import grupo5yomesumo.springboot.serializers.EventoDetalladoDTO
import grupo5yomesumo.springboot.serializers.EventoHomeDTO
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

    @GetMapping("all/pagina/{pagina}")
    @Operation(summary = "Devuelve todos los eventos")
    fun getAllEventos(@PathVariable pagina: Int): List<EventoDTO> = eventoService.getAllEventos(pagina).map{ EventoDTO(it) }

    @GetMapping("/{usuarioId}")
    @Operation(summary = "Devuelve eventos con filtro")
    fun getEventoFilter(@PathVariable usuarioId: Long, @RequestParam(value = "actividad") actividadNombre : String) : List<EventoHomeDTO> = eventoService.getEventoFilter(actividadNombre).map { EventoHomeDTO(it, solicitudService.habilitadaSolicitud(usuarioId, it.id)) }

    @GetMapping("/{usuarioId}/pagina/{pagina}")
    @Operation(summary = "Devuelve eventos con filtro")
    fun getEventoFilter(@PathVariable usuarioId: Long, @PathVariable pagina: Int, @RequestParam(value = "actividad") actividadNombre : String) : List<EventoHomeDTO> = eventoService.getEventoFilter(actividadNombre, pagina).map { EventoHomeDTO(it, solicitudService.habilitadaSolicitud(usuarioId, it.id)) }

    @GetMapping("{eventoId}/{usuarioId}")
    @Operation(summary = "Devuelve evento por id")
    fun getEvento(@PathVariable eventoId: Long, @PathVariable usuarioId: Long) : EventoDetalladoDTO {
        val evento = eventoService.getEvento(eventoId)
        val participantes =  solicitudService.solicitudesAceptadasDeEvento(eventoId)
        return EventoDetalladoDTO(evento, participantes, solicitudService.habilitadaSolicitud(usuarioId, eventoId))
    }

    //NO IRIA MAS?
    @GetMapping("usuario/{usuarioId}")
    @Operation(summary = "Devuelve los eventos de un usuario específico.")
    fun getEventosUsuario(@PathVariable usuarioId: Long): List<EventoDTO> = eventoService.getEventosByAnfitrion(usuarioId).map { EventoDTO(it) }

    @GetMapping("usuario/{usuarioId}/pagina/{pagina}")
    @Operation(summary = "Devuelve los eventos de un usuario específico.")
    fun getEventosUsuario(@PathVariable usuarioId: Long, @PathVariable pagina: Int): List<EventoDTO> = eventoService.getEventosByAnfitrion(usuarioId, pagina).map { EventoDTO(it) }

    //paginacion - por hacer
    @GetMapping("/{usuarioId}/eventosAsistidos")
    @Operation(summary = "Devuelve los eventos a los que fue un usuario que ya pasaron")
    fun getEventosAsistidos(@PathVariable usuarioId: Long) : List<EventoDTO> = solicitudService.getEventosAsistidosPor(usuarioId).map { EventoDTO(it) }

    @GetMapping("/{anfitrionId}/eventosCreados")
    @Operation(summary = "Devuelve los eventos que ya terminaron y creados por un usuario especifico")
    fun getEventosCreadosTerminados(@PathVariable anfitrionId: Long) : List<EventoDTO> = eventoService.getEventosTerminadosByAnfitrion(anfitrionId).map { EventoDTO(it) }

    @GetMapping("/{anfitrionId}/eventosCreados/pagina/{pagina}")
    @Operation(summary = "Devuelve los eventos que ya terminaron y creados por un usuario especifico")
    fun getEventosCreadosTerminados(@PathVariable anfitrionId: Long, @PathVariable pagina: Int) : List<EventoDTO> = eventoService.getEventosTerminadosByAnfitrion(anfitrionId, pagina).map { EventoDTO(it) }

    @PostMapping("crear")
    @Operation(summary = "Crea un evento")
    fun crearEvento(@RequestBody eventoProps: CrearEventProps) = eventoService.crearEvento(
        eventoProps.anfitrionId,
        eventoProps.actividadId,
        eventoProps.fecha,
        eventoProps.hora,
        eventoProps.descripcion,
        eventoProps.ubicacion,
        eventoProps.capacidadMaxima
    )

    @GetMapping("{eventoId}/solicitudes")
    @Operation(summary = "Devuelve las solicitudes de un evento")
    fun getSolicitudesDeEvento(@PathVariable eventoId: Long) : List<SolicitudDTO> = solicitudService.getSolicitudesByEvento(eventoId).map { SolicitudDTO(it) }

    @DeleteMapping("{eventoId}/borrar")
    @Operation(summary = "Elimina un evento")
    fun eliminarEvento(@PathVariable eventoId: Long) = eventoService.eliminarEvento(eventoId)

//    @GetMapping("pagina/{numero}")
//    fun getEventosPaginados(@PathVariable numero: Int) = eventoService.getAllEventos(numero)

}

data class CrearEventProps(
    val anfitrionId: Long,
    val actividadId: Long,
    val fecha: String,
    val hora: String,
    val descripcion: String,
    val ubicacion: UbicacionProps,
    val capacidadMaxima: Int,
)

data class UbicacionProps(
    val nombreCompletoLugar: String,
    val barrio: String,
    val lat: String,
    val lon: String
)