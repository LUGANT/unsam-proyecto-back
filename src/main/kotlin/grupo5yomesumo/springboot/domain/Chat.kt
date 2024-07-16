package grupo5yomesumo.springboot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


//data class Chat(
//    val id: String? = null,
//    val eventoId: Long? = null,
//    val userIds: List<Long>,
//    val mensajes: MutableList<Mensaje>
//)


@Document
data class Mensaje(
    @Id val id: String? = null,
    val usuarioId : Long,
    val eventoId: Long,
//    val horario : LocalDateTime,
    val texto : String
)
