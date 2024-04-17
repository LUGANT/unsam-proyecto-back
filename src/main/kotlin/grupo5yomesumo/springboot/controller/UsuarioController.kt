package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.serializers.UsuarioDTO
import grupo5yomesumo.springboot.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("usuario")
@CrossOrigin("*")
class UsuarioController(
    val usuarioService: UsuarioService
) {

    @GetMapping("")
    @Operation(summary = "Get todos los usuarios")
    fun getAllUsuarios() = usuarioService.getAllUsuarios().map { UsuarioDTO(it) }

}