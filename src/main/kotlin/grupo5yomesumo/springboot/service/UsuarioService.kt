package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    val usuarioRepository: UsuarioRepository
){
    fun getAllUsuarios(): List<Usuario> = usuarioRepository.findAll()
    fun getUsuario(usuarioId: Long): Usuario = usuarioRepository.getById(usuarioId)

}