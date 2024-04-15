package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import grupo5yomesumo.springboot.repository.Entidad
import java.time.LocalDate


class Evento(
    override var id: Long = 0,
    var anfitrion: String /*Usuario*/ = "",
    var actividad: String /*Actividad*/ = "",
    var fecha: LocalDate = LocalDate.now(),
    var direccion: String = "",
    val solicitudes: MutableList<String> /*MutableList<Usuario>*/ = mutableListOf(),
    val aceptados: MutableList<String> /*MutableList<Usuario>*/ = mutableListOf(),
): Entidad {

    fun activo(): Boolean = fecha.isAfter(LocalDate.now())

    fun participantes(): List<String/*Usuario*/> = aceptados + anfitrion

    fun responderSolicitud(invitado: String/*Usuario*/, respuesta: Boolean) {
        validarResponderSolicitud(invitado)
        if (respuesta) {
            solicitudes.remove(invitado)
            aceptados.add(invitado)
        } else {
            solicitudes.remove(invitado)
        }
    }

    private fun solicitudPuedeSerRespondida(invitado: String/*Usuario*/): Boolean = invitado in solicitudes

    private fun validarResponderSolicitud(invitado: String/*Usuario*/) {
        if (!solicitudPuedeSerRespondida(invitado)) {
            throw BusinessException("El usuario ya no está interesado en participar en el evento.") //Business o NotFound???
        }
    }

}
