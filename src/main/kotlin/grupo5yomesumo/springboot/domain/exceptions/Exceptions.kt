package grupo5yomesumo.springboot.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(message : String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message : String) : RuntimeException(message)

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
class NotAllowedException(message: String) : RuntimeException(message)
