package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.repository.Entidad
import jakarta.persistence.*

@Entity
data class Actividad(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id : Long = 0,
    val nombre : String = "",
    val esGrupal : Boolean = false
)

