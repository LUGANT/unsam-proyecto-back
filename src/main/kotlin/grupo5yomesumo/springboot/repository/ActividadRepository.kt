package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Actividad
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
class ActividadRepository : CrudRepo<Actividad>() {

    fun getActividadesGrupales() : List<Actividad> = objetos.filter { it.esGrupal }

}

//DEJO ARMADO ESTE PARA CUANDO TENGAMOS JPA
//interface Actividadrepository : CrudRepository<Actividad, Long>{
//
//    fun findActividadsByEnGrupoIsTrue() : List<Actividad>
//}