package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Usuario

class ParticipanteDTO(
    val id: Long,
    val username: String,
    val nombre: String,
    val apellido: String
) {
    constructor(usuario: Usuario):this(
        id = usuario.id,
        username = usuario.username,
        nombre = usuario.nombre,
        apellido = usuario.apellido
    )
}