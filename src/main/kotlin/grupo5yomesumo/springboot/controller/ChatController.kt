package grupo5yomesumo.springboot.controller


import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.serializers.MessageDTO
import grupo5yomesumo.springboot.service.MessageService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("chat")
@CrossOrigin("*")
class ChatController(
    val messageService : MessageService
) {

//    @GetMapping("{eventoId}")
//    @Operation(summary = "Trae el chat de un evento especifico")
//    fun getChatByEvento(@PathVariable eventoId : Long) : List<MessageDTO> = messageService.getMessages(eventoId).map { it.toDTO() }
//
//    fun Mensaje.toDTO(): MessageDTO{
//        val fechaHora = this.fechaHora.toString()
//        return MessageDTO(this.texto, this.username, this.usuarioId, this.eventoId, fechaHora)
//    }

}