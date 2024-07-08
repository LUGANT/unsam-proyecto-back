package grupo5yomesumo.springboot.controller


import grupo5yomesumo.springboot.config.SocketIOService
import grupo5yomesumo.springboot.domain.Mensaje
import grupo5yomesumo.springboot.service.ChatService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
@RequestMapping("chat")
@CrossOrigin("*")
class ChatController(
    val chatService : ChatService,
    val socketIOService: SocketIOService
) {

    @GetMapping("{eventoId}")
    @Operation(summary = "Trae el chat de un evento especifico")
    fun getChatByEvento(@PathVariable eventoId : Long) : List<Mensaje> = chatService.getChatByEvento(eventoId)

    @PostMapping("{eventoId}/newMessage")
    @Operation(summary = "Enviar un nuevo mensaje")
    fun postNuevoMensaje(@PathVariable eventoId : Long,
                         @RequestBody chatProps: ChatProps) = chatService.nuevoMensaje(eventoId, chatProps.usuarioId, chatProps.texto)

    @GetMapping("/send")
    fun sendMessage(): String {
        // Aquí puedes usar el socketIOService para emitir eventos a los clientes conectados
        socketIOService.broadcastMessage("chat message", SocketIOService.ChatMessage("Mensaje desde el servidor", 1))
        return "Mensaje enviado a los clientes"
    }

}

data class ChatProps(
    val usuarioId: Long,
    val texto: String
)