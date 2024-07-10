package grupo5yomesumo.springboot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Chat(
    @Id val id: String? = null,
    val eventoId: Long? = null,
    val userIds: List<Long>,
    val mensajes: MutableList<Mensaje>
)


data class Mensaje(
    val id: String? = null,
    val usuarioId : Long,
//    val horario : LocalDateTime,
    val texto : String
)
