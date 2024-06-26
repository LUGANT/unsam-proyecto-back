package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface Actividadrepository : CrudRepository<Actividad, Long>{

    fun findActividadsByEsGrupalIsTrue() : List<Actividad>

    fun findActividadsByNombreContains(actividadNombre : String) : List<Actividad>
}