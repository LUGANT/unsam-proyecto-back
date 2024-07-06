package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.serializers.OpinionDTO
import grupo5yomesumo.springboot.serializers.ReporteDTO
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
    fun getOpinionesDeUsuario(@PathVariable usuarioId: Long): List<OpinionDTO> = opinionService.getOpinionesByUsuario(usuarioId).map { OpinionDTO(it)}

    @PostMapping("nueva")
    @Operation(summary = "Crear nueva opinión.")
    fun crearOpinion(@RequestBody opinionProps: CrearOpinionProps) {
        opinionService.crearOpinion(
            opinionProps.puntaje,
            opinionProps.comentario,
            opinionProps.usuarioId,
            opinionProps.usuarioOpinadorId
        )
    }

    @PostMapping("reportar/{comentarioId}/{reportadorId}")
    @Operation(summary = "Permite reportar un comentario")
    fun reportarOpinion(@PathVariable comentarioId: Long, @PathVariable reportadorId: Long) = opinionService.reportarOpinion(comentarioId, reportadorId)

}

data class CrearOpinionProps(
    val puntaje: Int,
    val comentario: String,
    val usuarioId: Long,
    val usuarioOpinadorId : Long
)