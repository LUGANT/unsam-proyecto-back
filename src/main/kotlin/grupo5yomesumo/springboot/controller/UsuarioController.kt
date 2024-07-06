package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.config.JwtUtil
import grupo5yomesumo.springboot.domain.Sugerencia
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.serializers.EventoDTO
import grupo5yomesumo.springboot.serializers.PerfilDTO
import grupo5yomesumo.springboot.serializers.UsuarioDTO
import grupo5yomesumo.springboot.service.SolicitudService
import grupo5yomesumo.springboot.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("usuario")
@CrossOrigin("*")
class UsuarioController(
    val usuarioService: UsuarioService,
    val solicitudService: SolicitudService,
    val opinionController: OpinionController,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtUtil: JwtUtil
) {

    @PostMapping("login")
    @Operation(summary = "Devuelve un usuario en base a un nombre y una contraseña")
    fun login(@RequestBody unlogedUser: Usuario): ResponseEntity<*> {
        try {
            // Autenticar el usuario
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(unlogedUser.username, unlogedUser.password)
            )
        } catch (e: Exception) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos")
        }

        // Cargar los detalles del usuario
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(unlogedUser.username)

        // Generar el token JWT
        val jwt: String = jwtUtil.generateToken(userDetails)

        // Devolver el token en la respuesta
        return ResponseEntity.ok(mapOf("token" to jwt))
    }

    @PostMapping("signup")
    @Operation(summary = "Permite crear una cuenta")
    fun signup(@RequestBody usuario: Usuario): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(usuarioService.signUp(usuario))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(e.message)
        }
    }


    @GetMapping("")
    @Operation(summary = "Get todos los usuarios")
    fun getAllUsuarios() = usuarioService.getAllUsuarios().map { UsuarioDTO(it) }

    @GetMapping("{usuarioId}/perfil")
    @Operation(summary = "Trae todos los datos para llenar el perfil de un usuario especifico")
    fun getPerfil(@PathVariable usuarioId: Long) : PerfilDTO = PerfilDTO(getUsuario(usuarioId), opinionController.getOpinionesDeUsuario(usuarioId))

    @GetMapping("{usuarioId}")
    @Operation(summary = "Get usuario en concreto")
    fun getUsuario(@PathVariable usuarioId: Long) : UsuarioDTO = UsuarioDTO(usuarioService.getUsuario(usuarioId))

    @PostMapping("solicitud/crear/{eventoId}/{solicitanteId}")
    @Operation(summary = "crea la solicitud para un evento")
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

    @PatchMapping("/{usuarioId}/updateUsername")
    @Operation(summary = "Cambia el username de un usuario especifico")
    fun updateUsername(
        @PathVariable usuarioId: Long,
    @RequestParam(name = "nuevoUsername") nuevoUsername : String) = usuarioService.updateUsername(usuarioId, nuevoUsername)

    @GetMapping("user")
    @Operation(summary = "Devuelve los detalles del usuario autenticado")
    fun getUserDetails(@RequestHeader("Authorization") token: String): ResponseEntity<*> {
        try {
            // Validar el token y obtener el nombre de usuario
            val username = jwtUtil.extractUsername(token.substring(7)) // Quitar "Bearer "

            // Cargar los detalles del usuario
            val user = usuarioService.getUsuarioByUsername(username)

            // Crear la respuesta con los detalles del usuario
            val response = mapOf(
                "id" to user!!.id,
                "username" to user.username,
            )

            return ResponseEntity.ok(response)
        } catch (e: Exception) {
            return ResponseEntity.status(401).body("Token inválido o expirado")
        }
    }

    @PatchMapping("cambiarPassword")
    @Operation(summary = "Cambia la contraseña del usuario")
    fun updatePassword(@RequestBody cambioContrasenaRequest : CambioContrasenaRequest) =
        usuarioService.updatePassword(
            cambioContrasenaRequest.usuarioId,
            cambioContrasenaRequest.contrasenaActual,
            cambioContrasenaRequest.nuevaContrasena)


    @PostMapping("agregarSugerencia")
    @Operation(summary = "Permite agregar una sugerencia al usuario")
    fun addSugerencia(@RequestBody sugerenciaProps: SugerenciaProps) = usuarioService.addSugerencia(sugerenciaProps)


}

data class SugerenciaProps(
    val usuarioId: Long,
    val sugerencia: String
)

data class CambioContrasenaRequest(
    val usuarioId: Long,
    val contrasenaActual: String,
    val nuevaContrasena: String
)