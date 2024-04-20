package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.repository.Entidad

data class Actividad(
    override var id : Long = 0,
    val nombre : String,
    val esGrupal : Boolean
) : Entidad

