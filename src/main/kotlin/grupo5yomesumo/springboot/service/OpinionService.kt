package grupo5yomesumo.springboot.service

import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.domain.Reporte
import grupo5yomesumo.springboot.domain.Usuario
import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import grupo5yomesumo.springboot.repository.OpinionRepository
import grupo5yomesumo.springboot.repository.ReporteRepository
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class OpinionService(
    val opinionRepository: OpinionRepository,
    val usuarioService: UsuarioService,
    val reporteRepository: ReporteRepository
) {

    @Transactional
    fun reportarOpinion(opinionId: Long, reportadorId: Long){
        val reportador = usuarioService.getUsuario(reportadorId)
        val opinion = getOpinion(opinionId)
        validarPuedeReportar(reportadorId, opinionId)

        val reporte = Reporte(
            reportador = reportador,
            opinion = opinion
        )
        reporteRepository.save(reporte)
    }

    fun validarPuedeReportar(reportanteId: Long, opinionId: Long) {
        if (existeReporte(reportanteId, opinionId)) throw BusinessException("No se puede reportar el mismo comentario más de una vez")
    }

    fun existeReporte(reportanteId: Long, opinionId: Long) = reporteRepository.existsReporteByReportadorIdAndOpinionId(reportanteId, opinionId)

    fun getOpinion(opinionId: Long): Opinion = opinionRepository.findById(opinionId).orElseThrow { NotFoundException("No se pudo encontrar la opinión buscada.") }

    fun getOpinionesByUsuario(usuarioId: Long): List<Opinion> {
        val usuario = usuarioService.getUsuario(usuarioId)
        return opinionRepository.findOpinionesByUsuarioOpinado(usuario)
    }

    @Transactional
    fun crearOpinion(puntaje: Int, comentario: String, opinadoId: Long, opinanteId: Long) {
        validarPuntaje(puntaje)
        validarPuedeOpinar(opinadoId, opinanteId)
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

    fun validarPuedeOpinar(opinadoId: Long, opinanteId: Long) {
        if (existeOpinion(opinadoId, opinanteId)) throw BadRequestException("Ya dejaste una opinión sobre este usuario.")
    }

    fun existeOpinion(opinadoId: Long, opinanteId: Long) = opinionRepository.existsOpinionByUsuarioOpinadoIdAndUsuarioOpinanteId(opinadoId, opinanteId)
}