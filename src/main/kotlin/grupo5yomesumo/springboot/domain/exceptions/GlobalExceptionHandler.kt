package grupo5yomesumo.springboot.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUsernameNotFoundException(ex: UsernameNotFoundException): ResponseEntity<String> {
        return ResponseEntity("Usuario no encontrado", HttpStatus.NOT_FOUND)
    }

//    @ExceptionHandler(Exception::class)
//    fun handleGeneralException(ex: Exception): ResponseEntity<String> {
//        return ResponseEntity("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR)
//    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

}