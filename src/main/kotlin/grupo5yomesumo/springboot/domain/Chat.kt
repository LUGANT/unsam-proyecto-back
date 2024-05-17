package grupo5yomesumo.springboot.domain

import java.time.LocalDateTime

data class Chat(
    val id : String = "",
    val evento : Long,
    val Mensajes : List<Mensaje>
)

data class Mensaje(
    val id: Long,
    val usuario : Long,
    val horario : LocalDateTime,
    val texto : String
)
