package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.*
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OpinionRepository: CrudRepository<Opinion, Long> {

    override fun findById(id: Long): Optional<Opinion>

    fun findOpinionesByUsuario(usuario: Usuario) : List<Opinion>

}