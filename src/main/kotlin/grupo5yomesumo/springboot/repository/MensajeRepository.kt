package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Mensaje
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MensajeRepository : MongoRepository<Mensaje, String> {

    fun getMensajeByEventoId(evento: Long): List<Mensaje>

//    fun getMensajesByEventoOrderByFechaAsc(evento: Long) : List<Mensaje>

}