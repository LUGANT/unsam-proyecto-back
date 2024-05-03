package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import grupo5yomesumo.springboot.repository.Entidad
import java.time.LocalDate


class Evento(
    override var id: Long = 0,
    var anfitrion: Usuario/*Usuario*/,
    var actividad: Actividad/*Actividad*/,
    var fecha: LocalDate,
    var direccion: String,
    val solicitudes: MutableList<Usuario>/*<Usuario>*/ = mutableListOf(),
    val aceptados: MutableList<Usuario>/*<Usuario>*/ = mutableListOf(),
    // val CapacidadMaxima: Int
): Entidad {

    fun activo(): Boolean = fecha.isAfter(LocalDate.now())

    fun participantes(): List<Usuario/*Usuario*/> = aceptados + anfitrion

    fun responderSolicitud(invitado: Usuario/*Usuario*/, respuesta: Boolean) {
        validarResponderSolicitud(invitado)
        if (respuesta) {
            solicitudes.remove(invitado)
            aceptados.add(invitado)
        } else {
            solicitudes.remove(invitado)
        }
    }

    fun agregarSolicitud(invitado: Usuario){
        validarAgregarSolicitud(invitado)
        solicitudes.add(invitado)
    }

    private fun solicitudPuedeSerRespondida(invitado: Usuario/*Usuario*/): Boolean = invitado in solicitudes

    private fun validarResponderSolicitud(invitado: Usuario/*Usuario*/) {
        if (!solicitudPuedeSerRespondida(invitado)) {
            throw BusinessException("El usuario ya no está interesado en participar en el evento.") //Business o NotFound???
        }
    }

    private fun validarAgregarSolicitud(invitado: Usuario){
        if(solicitudPuedeSerRespondida(invitado)){
            throw BusinessException("El usuario ya se encuentra en la lista de solicitudes.")
        }
    }

}
