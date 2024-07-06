package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.repository.SugerenciaRepository
import grupo5yomesumo.springboot.serializers.ReporteDTO
import grupo5yomesumo.springboot.service.OpinionService
import grupo5yomesumo.springboot.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("admin")
@CrossOrigin("*")
class AdminController(
    val opinionService: OpinionService,
    val usuarioService: UsuarioService,
    val sugerenciaRepository: SugerenciaRepository
) {

    @GetMapping("getReportes")
    @Operation(summary = "Trae todos los reportes")
    fun getReportes() = opinionService.getReportes().map { ReporteDTO(it) }

    @GetMapping("getSugerencias")
    @Operation(summary = "Trae todas las sugerencias")
    fun getSugerencias() =  sugerenciaRepository.findAll()

}