package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.domain.Usuario

data class PerfilDTO(
    val usuario: UsuarioDTO,
    val opiniones : List<OpinionDTO>
)