package grupo5yomesumo.springboot.domain

data class Solicitud(
    var id : Long = 0,
    val solicitante : Usuario,
    val evento : Evento,
    val estado : Estado = Estado.PENDIENTE
)

enum class Estado{
    PENDIENTE, ACEPTADA, RECHAZADA
}