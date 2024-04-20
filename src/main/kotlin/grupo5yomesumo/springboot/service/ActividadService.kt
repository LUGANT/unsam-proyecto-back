package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.repository.ActividadRepository
import org.springframework.stereotype.Service

@Service
class ActividadService(
    val actividadRepository: ActividadRepository
) {

    fun getAllActividades() : MutableList<Actividad> = actividadRepository.findAll()

    fun getActividadById(actividadId : Long) : Actividad = actividadRepository.getById(actividadId)

    fun getAllActividadesFilter(esGrupal : Boolean) =
        if (!esGrupal) getAllActividades()
        else getActividadesGrupales()

    fun getActividadesGrupales() : List<Actividad> = actividadRepository.getActividadesGrupales()

}