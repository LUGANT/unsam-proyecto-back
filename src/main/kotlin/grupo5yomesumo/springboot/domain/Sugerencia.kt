package grupo5yomesumo.springboot.domain

import jakarta.persistence.GeneratedValue
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType

@Document(collection = "sugerencias")
data class Sugerencia(
    @Id var id: String? = null,
    val usuarioId: Long = 0,
    val sugerencia: String = ""
)