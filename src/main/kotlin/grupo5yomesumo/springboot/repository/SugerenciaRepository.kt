package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Sugerencia
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SugerenciaRepository : MongoRepository<Sugerencia, String>{

}