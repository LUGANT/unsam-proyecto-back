package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Reporte
import grupo5yomesumo.springboot.domain.Usuario

class ReporteDTO(
    val id: Long,
    val comentario: String,
    val reportado: UsuarioMinDTO,
    val reportador: UsuarioMinDTO
) {
    constructor(reporte: Reporte) : this(
        id = reporte.id,
        comentario = reporte.opinion.comentario,
        reportado = UsuarioMinDTO(reporte.opinion.usuarioOpinante),
        reportador = UsuarioMinDTO(reporte.reportador)
    )
}