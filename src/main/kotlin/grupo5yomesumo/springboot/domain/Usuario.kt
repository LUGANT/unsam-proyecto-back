package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.repository.Entidad

class Usuario(
    override var id: Long,
    val nombre: String,
    val apellido: String,
    val dni: Int
    ): Entidad {
    /* Por ahora parece que no tiene alguna responsabilidad */
}