package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : CrudRepository<Usuario, Long> {

    fun findByUsernameAndPassword(username: String, password: String): Usuario?

    fun existsUsuarioByUsername(username: String): Boolean

}