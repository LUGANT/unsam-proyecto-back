package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.Opinion
import grupo5yomesumo.springboot.domain.Reporte
import grupo5yomesumo.springboot.domain.Usuario
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReporteRepository: CrudRepository<Reporte, Long> {

    fun existsReporteByReportadorAndOpinion(reportador: Usuario, opinion: Opinion) : Boolean

}