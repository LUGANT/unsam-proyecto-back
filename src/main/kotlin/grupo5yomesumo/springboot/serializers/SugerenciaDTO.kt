package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Sugerencia

class SugerenciaDTO(
    val id: String,
    val usuario: Long,
    val sugerencia: String
) {
    constructor(sugerencia: Sugerencia): this(
        id = sugerencia.id!!,
        usuario = sugerencia.usuarioId,
        sugerencia = sugerencia.sugerencia
    )
}