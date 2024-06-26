package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Usuario
import org.apache.logging.log4j.util.StringMap

class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val username: String,
    val puntuacion : Double
) {
    constructor(usuario: Usuario):this(
        id = usuario.id,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        username = usuario.username,
        puntuacion = usuario.puntuacion
    )
}

class UsuarioMinDTO(
    val id: Long,
    val username : String,
    val nombre: String,
    val apellido: String,
    val opinable: Boolean
){
    constructor(usuario: Usuario):this(
        id = usuario.id,
        username = usuario.username,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        opinable = true
    )

    constructor(usuario: Usuario, opinable: Boolean):this(
        id = usuario.id,
        username = usuario.username,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        opinable = opinable
    )

}