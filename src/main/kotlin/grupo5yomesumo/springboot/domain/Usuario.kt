package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.repository.Entidad

class Usuario(
    override var id: Long = 0,
    val nombre: String,
    val apellido: String,
    val username: String,
    val password: String,
    ): Entidad {
    /* Por ahora parece que no tiene alguna responsabilidad */
}