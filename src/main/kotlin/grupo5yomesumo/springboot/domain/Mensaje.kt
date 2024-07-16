package grupo5yomesumo.springboot.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document
data class Mensaje(
    @Id var id: String? = null,
    var eventoId: Long,
    var usuarioId : Long,
    var username: String,
    var userProfile: String = "",
    var fechaHora: LocalDateTime = LocalDateTime.now(),
    var texto : String
)
