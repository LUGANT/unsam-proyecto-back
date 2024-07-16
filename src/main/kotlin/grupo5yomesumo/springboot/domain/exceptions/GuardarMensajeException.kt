package grupo5yomesumo.springboot.domain.exceptions

class GuardarMensajeException(message: String, e: RuntimeException): RuntimeException(message, e) {
}