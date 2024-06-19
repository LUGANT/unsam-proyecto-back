package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import jakarta.persistence.*

@Entity
class Solicitud(
    @Id @GeneratedValue val id : Long = 0,
    @ManyToOne val solicitante : Usuario = Usuario(),
    @ManyToOne val evento : Evento = Evento()
){
    @Enumerated(EnumType.STRING) var estado : Estado = Estado.PENDIENTE

    private fun validarResponderSolicitud() {
        if (estado != Estado.PENDIENTE) throw BusinessException("La solicitud ya fue respondida.")
        if (!evento.activo()) throw BusinessException("El evento ya terminó o está en curso.")
    }

    fun responderSolicitud(aceptada: Boolean){
        validarResponderSolicitud()
        this.estado = getEstadoSolicitud(aceptada)
    }

    fun getEstadoSolicitud(aceptada: Boolean) = if (aceptada) Estado.ACEPTADA else Estado.RECHAZADA

}

enum class Estado{
    PENDIENTE, ACEPTADA, RECHAZADA
}