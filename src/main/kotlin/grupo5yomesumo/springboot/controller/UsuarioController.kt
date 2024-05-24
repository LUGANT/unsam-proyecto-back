package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.serializers.UsuarioDTO
import grupo5yomesumo.springboot.service.SolicitudService
import grupo5yomesumo.springboot.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("usuario")
@CrossOrigin("*")
class UsuarioController(
    val usuarioService: UsuarioService,
    val solicitudService: SolicitudService
) {

    @PostMapping("login")
    @Operation(summary = "Devuelve un usuario en base a un nombre y una contraseña")
    fun getUser(
        @RequestBody unlogedUser: Usuario
    ) = UsuarioDTO(usuarioService.logIn(unlogedUser.username,unlogedUser.password))

    @GetMapping("")
    @Operation(summary = "Get todos los usuarios")
    fun getAllUsuarios() = usuarioService.getAllUsuarios().map { UsuarioDTO(it) }

    @GetMapping("{usuarioId}")
    @Operation(summary = "Get usuario en concreto")
    fun getUsuario(@PathVariable usuarioId: Long) = usuarioService.getUsuario(usuarioId)

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
}