package grupo5yomesumo.springboot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

//data class Chat(
//    val id : String,
//    val evento : Long,
//    val Mensajes : List<Mensaje>
//)

@Document
data class Mensaje(
    @Id val id: String,
    @Field("eventoId") val evento: Long,
    @Field("usuarioId") val usuario : Long,
    val horario : LocalDateTime,
    val texto : String
)
