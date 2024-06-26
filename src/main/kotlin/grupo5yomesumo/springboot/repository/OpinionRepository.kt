package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface OpinionRepository: CrudRepository<Opinion, Long> {

    override fun findById(id: Long): Optional<Opinion>

    fun findOpinionesByUsuarioOpinado(usuario: Usuario) : List<Opinion>

    @Query("SELECT COALESCE(AVG(o.puntaje), 0) from Opinion o where o.usuarioOpinado = :usuario")
    fun calcularPromedioPuntaje(@Param("usuario") usuario : Usuario) : Double

    fun existsOpinionByUsuarioOpinadoIdAndUsuarioOpinanteId(opinadoId: Long, opinante: Long): Boolean

}