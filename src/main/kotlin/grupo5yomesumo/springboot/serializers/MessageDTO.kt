package grupo5yomesumo.springboot.serializers

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import grupo5yomesumo.springboot.domain.Mensaje
import java.time.LocalDate
import java.time.LocalTime

data class MessageDTO @JsonCreator constructor (
    @JsonProperty("texto") val texto: String = "",
    @JsonProperty("username") val username: String = "",
    @JsonProperty("usuarioId") val usuarioId: Long = 0,
    @JsonProperty("imgUrl") val imgUrl: String = "",
    @JsonProperty("eventoId") val eventoId: Long = 0,
    @JsonProperty("fecha") val fecha: String = "",
    @JsonProperty("hora") val hora: String = ""
)