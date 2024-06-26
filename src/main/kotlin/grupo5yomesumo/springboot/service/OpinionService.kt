package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.OpinionRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class OpinionService(
    val opinionRepository: OpinionRepository,
    val usuarioService: UsuarioService
) {

    fun getOpinion(opinionId: Long) = opinionRepository.findById(opinionId).orElseThrow { NotFoundException("No se pudo encontrar la opinión buscada.") }

    fun getOpinionesByUsuario(usuarioId: Long): List<Opinion> {
        val usuario = usuarioService.getUsuario(usuarioId)
        return opinionRepository.findOpinionesByUsuarioOpinado(usuario)
    }

    @Transactional
    fun crearOpinion(puntaje: Int, comentario: String, opinadoId: Long, opinanteId: Long) {
        validarPuntaje(puntaje)
        validarUsuarioYaOpinado(opinadoId, opinanteId)
        val opinado = usuarioService.getUsuario(opinadoId)
        val opinante = usuarioService.getUsuario(opinanteId)
        val nuevaOpinion = Opinion(puntaje = puntaje, comentario = comentario, usuarioOpinado = opinado, usuarioOpinante = opinante)
        opinionRepository.save(nuevaOpinion)

        calcularPromedioPuntaje(opinado)
        usuarioService.save(opinado)
    }

    private fun calcularPromedioPuntaje(usuario : Usuario){
        usuario.puntuacion = opinionRepository.calcularPromedioPuntaje(usuario)
    }

    private fun validarPuntaje(puntaje: Int) {
        if (!puntajeValido(puntaje)) throw BadRequestException("Debe insertar un puntaje válido.")
    }

    private fun puntajeValido(puntaje: Int) = puntaje in arrayOf(1, 2, 3, 4, 5)

    fun validarUsuarioYaOpinado(opinadoId: Long, opinanteId: Long) {
        if (usuarioNoOpinable(opinadoId, opinanteId)) throw BadRequestException("Ya dejaste una opinión sobre este usuario.")
    }

    fun usuarioNoOpinable(opinadoId: Long, opinanteId: Long) = opinionRepository.existsOpinionByUsuarioOpinadoIdAndUsuarioOpinanteId(opinadoId, opinanteId)
}