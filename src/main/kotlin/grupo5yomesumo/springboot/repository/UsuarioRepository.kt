package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioRepository : CrudRepository<Usuario, Long> {

    fun existsUsuarioByUsername(username: String): Boolean

    fun findByUsername(username: String) : Usuario?

    fun getUsuarioByUsername(username: String) : Usuario?

    fun findUsuarioByUsername(username: String) : Optional<Usuario>

}