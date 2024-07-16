package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Usuario

class UsuarioDTO(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val username: String,
    val puntuacion : Double,
    val imgUrl: String
) {
    constructor(usuario: Usuario):this(
        id = usuario.id,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        username = usuario.username,
        puntuacion = usuario.puntuacion,
        imgUrl = usuario.imgUrl
    )
}

class UsuarioMinDTO(
    val id: Long,
    val username : String,
    val nombre: String,
    val apellido: String,
    val existeOpinion: Boolean,
    val imgUrl: String
){
    constructor(usuario: Usuario):this(
        id = usuario.id,
        username = usuario.username,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        existeOpinion = true,
        imgUrl = usuario.imgUrl
    )

    constructor(usuario: Usuario, existeOpinion: Boolean):this(
        id = usuario.id,
        username = usuario.username,
        nombre = usuario.nombre,
        apellido = usuario.apellido,
        existeOpinion = existeOpinion,
        imgUrl = usuario.imgUrl
    )

}