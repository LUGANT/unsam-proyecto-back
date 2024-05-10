package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.service.OpinionService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("valoracion")
@CrossOrigin("*")
class OpinionController(
    val opinionService: OpinionService
) {

    @GetMapping("{usuarioId}")
    @Operation(summary = "Devuelve todas las opiniones de un usuario.")
    fun getOpinionesByUsuario(@PathVariable usuarioId: Long): List<Opinion> = opinionService.getOpinionesByUsuario(usuarioId)

    @PostMapping("nueva")
    @Operation(summary = "Crear nueva opinión.")
    fun crearOpinion(@RequestBody opinionProps: CrearOpinionProps) {
        opinionService.crearOpinion(
            opinionProps.puntaje,
            opinionProps.comentario,
            opinionProps.usuarioId
        )
    }
}

data class CrearOpinionProps(
    val puntaje: Int,
    val comentario: String,
    val usuarioId: Long
)