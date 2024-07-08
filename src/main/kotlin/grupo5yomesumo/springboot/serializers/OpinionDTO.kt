package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Actividad
import grupo5yomesumo.springboot.domain.Evento
import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.domain.Usuario
import java.time.LocalDate

class OpinionDTO(
    val id: Long,
    val opinante: UsuarioMinDTO,
    val opinado: UsuarioMinDTO,
    val comentario: String,
    val puntaje : Int,
    val fecha: LocalDate,
    val existeReporte: Boolean
) {

    constructor(opinion: Opinion, existeReporte: Boolean): this (
        id = opinion.id,
        opinante = UsuarioMinDTO(opinion.usuarioOpinante),
        opinado = UsuarioMinDTO(opinion.usuarioOpinado),
        comentario = opinion.comentario,
        puntaje = opinion.puntaje,
        fecha = opinion.fecha,
        existeReporte = existeReporte
    )

}

