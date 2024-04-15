package grupo5yomesumo.springboot.domain

import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import java.time.LocalDate


class Evento(
    var anfitrion: String /*Usuario*/ = "",
    var actividad: String /*Actividad*/ = "",
    var fecha: LocalDate = LocalDate.now(),
    var direccion: String = "",
    val solicitudes: MutableList<String> /*MutableList<Usuario>*/ = mutableListOf(),
    val aceptados: MutableList<String> /*MutableList<Usuario>*/ = mutableListOf(),
) {

    fun termino(): Boolean = LocalDate.now().isAfter(fecha)

    fun participantes(): List<String> = aceptados + anfitrion

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
            throw BusinessException("El usuario ya no está interesado en participar en el evento.") //Business o NotFound??? Lo pense como bad request pq es como q esta queriendo responder algo que no esta, y para q el notFound quede para otras cosas
        }
    }

}
