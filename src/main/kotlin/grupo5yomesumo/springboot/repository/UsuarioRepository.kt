package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.stereotype.Repository

@Repository
class UsuarioRepository: CrudRepo<Usuario>() {
}