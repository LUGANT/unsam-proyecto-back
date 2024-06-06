package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Ubicacion
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UbicacionRepository : CrudRepository<Ubicacion, Long> {

}