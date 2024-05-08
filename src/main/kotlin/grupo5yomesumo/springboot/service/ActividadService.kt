package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.Actividadrepository
import org.springframework.stereotype.Service

@Service
class ActividadService(
    val actividadRepository: Actividadrepository
) {

    fun getAllActividades() : List<Actividad> = actividadRepository.findAll().toList()

    fun getActividad(actividadId : Long) : Actividad = actividadRepository.findById(actividadId).orElseThrow { NotFoundException("No se encontro actividad con el id $actividadId") }

    fun getAllActividadesFilter(esGrupal : Boolean) =
        if (!esGrupal) getAllActividades()
        else getActividadesGrupales()

    fun getActividadesGrupales() : List<Actividad> = actividadRepository.findActividadsByEsGrupalIsTrue()

}