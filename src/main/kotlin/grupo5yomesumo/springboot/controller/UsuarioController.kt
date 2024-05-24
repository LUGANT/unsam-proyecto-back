package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.serializers.EventoDTO
import grupo5yomesumo.springboot.serializers.UsuarioDTO
import grupo5yomesumo.springboot.service.SolicitudService
import grupo5yomesumo.springboot.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("usuario")
@CrossOrigin("*")
class UsuarioController(
    val usuarioService: UsuarioService,
    val solicitudService: SolicitudService
) {

    @GetMapping("")
    @Operation(summary = "Get todos los usuarios")
    fun getAllUsuarios() = usuarioService.getAllUsuarios().map { UsuarioDTO(it) }

    @GetMapping("{usuarioId}")
    @Operation(summary = "Get usuario en concreto")
    fun getUsuario(@PathVariable usuarioId: Long) : UsuarioDTO = UsuarioDTO(usuarioService.getUsuario(usuarioId))

    @PostMapping("solicitud/crear/{eventoId}/{solicitanteId}")
    @Operation(summary = "responde la solicitud de un evento")
    fun crearSolicitud(
        @PathVariable eventoId: Long,
        @PathVariable solicitanteId: Long
    ) = solicitudService.crear(eventoId, solicitanteId)

    @PatchMapping("solicitud/responder/{solicitudId}")
    @Operation(summary = "responde la solicitud de un evento")
    fun responderSolicitud(
        @PathVariable solicitudId: Long,
        @RequestParam aceptada: Boolean
    ) = solicitudService.responder(solicitudId, aceptada)

    @GetMapping("/{usuarioId}/solicitudes")
    @Operation(summary = "Devuelve los eventos a los cuales un usuario especifico hizo la solicitud")
    fun solicitudesDeUsuario(@PathVariable usuarioId: Long) : List<EventoDTO> = solicitudService.solicitudesDeUsuario(usuarioId).map { EventoDTO(it)}


}