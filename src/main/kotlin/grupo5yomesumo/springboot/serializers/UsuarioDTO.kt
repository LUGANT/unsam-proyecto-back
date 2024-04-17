package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Usuario
import org.apache.logging.log4j.util.StringMap

class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val username: String
) {
    constructor(usuario: Usuario):this(
        id = usuario.id,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        username = usuario.username
    )
}