package grupo5yomesumo.springboot.controller


import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.service.ChatService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("chat")
@CrossOrigin("*")
class ChatController(
    val chatService : ChatService
) {

    @GetMapping("{eventoId}")
    @Operation(summary = "Trae el chat de un evento especifico")
    fun getChatByEvento(@PathVariable eventoId : Long) : List<Mensaje> = chatService.getHistorial(eventoId)


}