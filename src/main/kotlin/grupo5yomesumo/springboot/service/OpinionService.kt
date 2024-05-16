package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.OpinionRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OpinionService(
    val opinionRepository: OpinionRepository,
    val usuarioService: UsuarioService
) {

    fun getOpinion(opinionId: Long) = opinionRepository.findById(opinionId).orElseThrow { NotFoundException("No se pudo encontrar la opinión buscada.") }

    fun getOpinionesByUsuario(usuarioId: Long): List<Opinion> {
        val usuario = usuarioService.getUsuario(usuarioId)
        return opinionRepository.findOpinionesByUsuario(usuario)
    }

    @Transactional
    fun crearOpinion(puntaje: Int, comentario: String, usuarioId: Long) {
        validarPuntaje(puntaje)
        val usuario = usuarioService.getUsuario(usuarioId)
        val nuevaOpinion = Opinion(puntaje = puntaje, comentario = comentario, usuario = usuario)
        opinionRepository.save(nuevaOpinion)
    }

    private fun validarPuntaje(puntaje: Int) {
        if (!puntajeValido(puntaje)) throw BadRequestException("Debe insertar un puntaje válido.")
    }

    private fun puntajeValido(puntaje: Int) = puntaje == 1 || puntaje == 2 || puntaje == 3 || puntaje == 4 || puntaje == 5
}