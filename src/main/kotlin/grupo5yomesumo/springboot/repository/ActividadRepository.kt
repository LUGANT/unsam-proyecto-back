package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

//@Repository
//class ActividadRepository : CrudRepo<Actividad>() {
//
//    fun getActividadesGrupales() : List<Actividad> = objetos.filter { it.esGrupal }
//
//}
@Repository
interface Actividadrepository : CrudRepository<Actividad, Long>{

    fun findActividadsByEsGrupalIsTrue() : List<Actividad>
}