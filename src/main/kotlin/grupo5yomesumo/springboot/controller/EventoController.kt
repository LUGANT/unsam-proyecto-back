package grupo5yomesumo.springboot.controller

import grupo5yomesumo.springboot.domain.Evento
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import grupo5yomesumo.springboot.service.EventoService
import org.springframework.web.bind.annotation.GetMapping


@RestController()
@RequestMapping("evento")
@CrossOrigin("*")
class UsuarioController(
    val eventoService: EventoService
) {

    @GetMapping("all")
    @Operation(summary = "Devuelve todos los eventos")
    fun getAllEventos(): List<Evento/*DTO*/> = eventoService.getAllEventos()//.map{ EventoDTO(it) }


}