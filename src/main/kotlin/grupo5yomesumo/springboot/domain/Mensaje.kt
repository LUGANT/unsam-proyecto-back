package grupo5yomesumo.springboot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDate
import java.time.LocalTime

@Document
data class Mensaje(
    @Id val id: String? = null,
    @Field("eventoId") val evento: Long,
    @Field("usuarioId") val usuario : Long,
    val username: String,
    val fecha : LocalDate,
    val horario : LocalTime,
    val texto : String
)
