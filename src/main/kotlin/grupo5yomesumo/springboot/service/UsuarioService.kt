package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    val usuarioRepository: UsuarioRepository
){
    fun save(usuario: Usuario) = usuarioRepository.save(usuario)

    fun getAllUsuarios(): List<Usuario> = usuarioRepository.findAll().toList()

    fun getUsuario(usuarioId: Long): Usuario = usuarioRepository.findById(usuarioId).orElseThrow{NotFoundException("No se encontro el usuario con el id $usuarioId")}


}