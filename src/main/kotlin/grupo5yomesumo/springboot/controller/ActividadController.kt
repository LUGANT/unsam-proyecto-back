package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.service.ActividadService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("actividad")
@CrossOrigin("*")
class ActividadController(
    val actividadService: ActividadService
) {

    @GetMapping("all")
    @Operation(summary = "Trae todas las actividades")
    fun getAllActividades() : List<Actividad> = actividadService.getAllActividades()

    @GetMapping("/{actividadId}")
    @Operation(summary = "Trae una actividad por id")
    fun getActividadById(@PathVariable actividadId : Long) : Actividad = actividadService.getActividad(actividadId)

    @GetMapping("filterGrupal")
    @Operation(summary = "Trae las actividades filtradas en si son en grupo")
    fun getAllActividadesFilter(@RequestParam(name = "esGrupal") esGrupal : Boolean) : List<Actividad> = actividadService.getAllActividadesFilter(esGrupal)

}