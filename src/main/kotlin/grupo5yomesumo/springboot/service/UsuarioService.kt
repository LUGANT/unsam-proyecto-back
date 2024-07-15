package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.controller.SugerenciaProps
import grupo5yomesumo.springboot.controller.UsuarioProps
import grupo5yomesumo.springboot.domain.Sugerencia
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.SugerenciaRepository
import grupo5yomesumo.springboot.repository.UsuarioRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(
    val usuarioRepository: UsuarioRepository,
    val passwordEncoder: PasswordEncoder,
    val sugerenciaRepository: SugerenciaRepository
){

    fun logIn(username: String, password: String): Usuario {
        val usuario = usuarioRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Usuario no encontrado")

        if (!passwordEncoder.matches(password, usuario.password)) {
            throw UsernameNotFoundException("Contraseña incorrecta")
        }

        return usuario
    }

    @Transactional
    fun save(usuario: Usuario) = usuarioRepository.save(usuario)

    fun getAllUsuarios(): List<Usuario> = usuarioRepository.findAll().toList()

    fun getUsuario(usuarioId: Long): Usuario = usuarioRepository.findById(usuarioId).orElseThrow{NotFoundException("No se encontro el usuario con el id $usuarioId")}

    @Transactional
    fun signUp(usuario: Usuario) : Usuario {
        validarUsername(usuario.username)
        usuario.password = passwordEncoder.encode(usuario.password)
        return save(usuario)
    }

    fun validarUsername(username: String){
        val existe: Boolean = usuarioRepository.existsUsuarioByUsername(username)
        if (existe) throw IllegalArgumentException("Ya existe un usuario con ese nombre")
    }



    fun findByUsername(username: String): Usuario = usuarioRepository.findUsuarioByUsername(username).orElseThrow { NotFoundException("No se encontró usuario con ese username") }


    @Transactional
    fun updatePassword(usuarioId: Long, passwordVieja: String, passwordNueva: String){
        val usuario : Usuario = getUsuario(usuarioId)

        if (!passwordEncoder.matches(passwordVieja, usuario.password)){
            throw BusinessException("La contraseña actual es incorrecta")
        }

        if (passwordEncoder.matches(passwordNueva, usuario.password)) {
            throw BusinessException("La contraseña nueva no puede ser igual a la actual")
        }

        val passwordNuevaEncriptada = passwordEncoder.encode(passwordNueva)
        usuario.password = passwordNuevaEncriptada
        usuarioRepository.save(usuario)
    }

    fun getUsuarioByUsername(username: String) : Usuario? = usuarioRepository.findByUsername(username)

    @Transactional
    fun addSugerencia(sugerenciaProps: SugerenciaProps){
        val sugerencia = Sugerencia(
            usuarioId = sugerenciaProps.usuarioId,
            sugerencia = sugerenciaProps.sugerencia
        )

        sugerenciaRepository.save(sugerencia)
    }

    @Transactional
    fun updateUser(usuarioProps: UsuarioProps, usuarioId: Long){
        val usuario = getUsuario(usuarioId)

        if (usuarioProps.imgUrl.isNotEmpty()){
            usuario.usrImg = usuarioProps.imgUrl
            save(usuario)
        }
        if (usuarioProps.username.isNotEmpty()){
            updateUsername(usuarioId, usuarioProps.username)
        }
    }

    @Transactional
    fun updateUsername(usuarioId: Long, nuevoUsername : String){
        val usuario = getUsuario(usuarioId)
        validarUsername(nuevoUsername)
        usuario.username = nuevoUsername
        save(usuario)
    }

}