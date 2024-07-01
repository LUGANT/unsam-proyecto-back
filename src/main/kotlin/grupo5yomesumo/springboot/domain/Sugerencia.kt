package grupo5yomesumo.springboot.domain

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Sugerencia(
    @Id val id: String,
    val usuarioId: Long,
    val sugerencia: String
)