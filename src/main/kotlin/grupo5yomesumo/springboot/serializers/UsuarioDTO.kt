package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Usuario
import org.apache.logging.log4j.util.StringMap

class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val username: String,
    val puntuacion : Double,
    val usrImg: String
) {
    constructor(usuario: Usuario):this(
        id = usuario.id,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        username = usuario.username,
        puntuacion = usuario.puntuacion,
        usrImg = usuario.usrImg
    )
}

class UsuarioMinDTO(
    val id: Long,
    val username : String,
    val nombre: String,
    val apellido: String,
    val existeOpinion: Boolean,
    val usrImg: String
){
    constructor(usuario: Usuario):this(
        id = usuario.id,
        username = usuario.username,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        existeOpinion = true,
        usrImg = usuario.usrImg
    )

    constructor(usuario: Usuario, existeOpinion: Boolean):this(
        id = usuario.id,
        username = usuario.username,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        existeOpinion = existeOpinion,
        usrImg = usuario.usrImg
    )

}