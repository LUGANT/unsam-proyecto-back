package grupo5yomesumo.springboot.domain

import jakarta.persistence.*

@Entity
data class Actividad(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id : Long = 0,
    @Column(length = 50) val nombre : String = "",
    @Column val esGrupal : Boolean = false,
    @Enumerated(EnumType.STRING) val tipo : TipoActividad = TipoActividad.OTROS
)

enum class TipoActividad{
    DEPORTE, JUEGODEMESA, OTROS
}


