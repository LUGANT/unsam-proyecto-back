package grupo5yomesumo.springboot

import grupo5yomesumo.springboot.domain.*
import grupo5yomesumo.springboot.repository.*
import grupo5yomesumo.springboot.service.EventoService

import grupo5yomesumo.springboot.service.UsuarioService
import grupo5yomesumo.springboot.service.OpinionService
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.geo.Point
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime


@Component
class Bootstrap(
    val eventoRepository: EventoRepository,
    val usuarioRepository: UsuarioRepository,
    val actividadRepository: Actividadrepository,
    val solicitudRepository: SolicitudRepository,
    val mensajeRepository: MensajeRepository,
    val usuarioService: UsuarioService,
    val ubicacionRepository: UbicacionRepository,
    val opinionRepository: OpinionRepository,
    val opinionService: OpinionService
) : InitializingBean
{
    override fun afterPropertiesSet() {
        //EVENTOS
        clearMongoRepositories()

        //USUARIOS
        val usuarioA = Usuario(nombre = "Felipe", apellido = "Gamaleri", username = "feli", password = "1234")
        val usuarioB = Usuario(nombre = "Nicolas", apellido = "Masuyama", username = "masu", password = "1234")
        val usuarioC = Usuario(nombre = "Lucas", apellido = "Antenni", username = "lugant", password = "1234")
        val usuarioD = Usuario(nombre = "Rodrigo", apellido = "Tartaglia", username = "rodri", password = "1234")
        val usuarioE = Usuario(nombre = "Nicolas", apellido = "Stebner", username = "nico", password = "1234")
        val usuarioF = Usuario(nombre = "Gonzalo", apellido = "Mendez", username = "gonm", password = "1234")
        val usuarioG = Usuario(nombre = "Juan", apellido = "Pérez", username = "juan", password = "1234")
        val usuarioH = Usuario(nombre = "Ana", apellido = "Gomez", username = "anag", password = "1234")
        val usuarioI = Usuario(nombre = "Sofia", apellido = "Martinez", username = "sofia", password = "1234")
        val usuarioJ = Usuario(nombre = "Carlos", apellido = "Lopez", username = "carlos", password = "1234")


        arrayOf(
            usuarioA, usuarioB, usuarioC, usuarioD, usuarioE, usuarioF, usuarioG, usuarioH, usuarioI, usuarioJ
        ).forEach { usuarioService.signUp(it) }

        //ACTIVIDADES
        val basquet = Actividad(nombre = "Basquet", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val futbol = Actividad(nombre = "Futbol", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val tenisIndividual = Actividad(nombre = "Tenis individual", esGrupal = false, tipo = TipoActividad.DEPORTE)
        val tenisDobles = Actividad(nombre = "Tenis dobles", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val handball = Actividad(nombre = "Handball", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val voleibol = Actividad(nombre = "Voleibol", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val voleibolPlaya = Actividad(nombre = "Voleibol de playa", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val natacion = Actividad(nombre = "Natacion", esGrupal = false, tipo = TipoActividad.OTROS)
        val ciclismo = Actividad(nombre = "Ciclismo", esGrupal = false, tipo = TipoActividad.OTROS)
        val ajedrez = Actividad(nombre = "Ajedrez", esGrupal = false, tipo = TipoActividad.JUEGODEMESA)
        val rugby = Actividad(nombre = "Rugby", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val pingPong = Actividad(nombre = "Ping Pong", esGrupal = false, tipo = TipoActividad.DEPORTE)
        val beisbol = Actividad(nombre = "Beisbol", esGrupal = true, tipo = TipoActividad.DEPORTE)
        val patinaje = Actividad(nombre = "Patinaje", esGrupal = false, tipo = TipoActividad.OTROS)

        arrayOf(
            basquet, futbol, tenisIndividual, tenisDobles, handball, voleibol, voleibolPlaya, natacion, ciclismo, ajedrez, rugby, pingPong, beisbol, patinaje
        ).forEach { actividadRepository.save(it) }

        //UBICACIONES
        val sportivoBallester = Ubicacion(
            nombreLugar = "Club Sportivo Ballester, Gral. Roca 3123, Villa Ballester, Buenos Aires Province B1653, Argentina",
            coordenadas = Point(-34.550822, -58.561222),
            barrio = "Villa Ballester"
        )
        val plazaMitre = Ubicacion(
            nombreLugar = "Plaza Mitre, Villa Ballester, Buenos Aires Province B1653, Argentina",
            coordenadas = Point(-34.541320588450475, -58.56214291249878),
            barrio = "Villa Ballester"
        )
        arrayOf(
            sportivoBallester, plazaMitre
        ).forEach { ubicacionRepository.save(it) }

        //EVENTOS
        val futbolConLosPibes = Evento(descripcion = "Fulbito tranqui con los pibes", anfitrion = usuarioA, actividad = futbol, fecha = LocalDate.now().plusDays(1), hora = LocalTime.of(20, 30), ubicacion = plazaMitre, capacidadMaxima = 10)
        val basquet3Vs3 = Evento(descripcion = "Basquet por la coca", anfitrion = usuarioB, actividad = basquet, fecha = LocalDate.now().plusDays(1), hora = LocalTime.of(17, 25), ubicacion = plazaMitre, capacidadMaxima = 6)
        val eventoTerminado = Evento(descripcion = "Partido de futbol en cancha de 7", anfitrion = usuarioC, actividad = futbol, fecha = LocalDate.now().minusDays(1), hora = LocalTime.of(14,0), ubicacion = sportivoBallester, capacidadMaxima = 12)
        val tenisSingles = Evento(descripcion = "Alguien para jugar al tenis porfa", anfitrion = usuarioJ, actividad = tenisIndividual, fecha = LocalDate.now().plusDays(2), hora = LocalTime.of(15, 0), ubicacion = sportivoBallester, capacidadMaxima = 2)
        val voleibolALaTarde = Evento(descripcion = "Un voley entre amigos", anfitrion = usuarioF, actividad = voleibol, fecha = LocalDate.now().plusDays(1), hora = LocalTime.of(18, 0), ubicacion = sportivoBallester, capacidadMaxima = 4)
        val ajedrezEnLaPlaza = Evento(descripcion = "Juntada para jugadores de ajedrez", anfitrion = usuarioD, actividad = ajedrez, fecha = LocalDate.now().plusDays(1), hora = LocalTime.of(10, 0), ubicacion = plazaMitre, capacidadMaxima = 4)
        val pingPongEn4Dias = Evento(descripcion = "Me pinto jugar al ping pong alguien le copa?", anfitrion = usuarioB, actividad = pingPong, fecha = LocalDate.now().plusDays(4), hora = LocalTime.of(19, 30), ubicacion = sportivoBallester, capacidadMaxima = 2)


        arrayOf(
            futbolConLosPibes, basquet3Vs3, eventoTerminado, tenisSingles, voleibolALaTarde, ajedrezEnLaPlaza, pingPongEn4Dias
        ).forEach { eventoRepository.save(it) }

        //SOLICITUDES
        val solicitud1 = Solicitud(solicitante = usuarioE, evento = futbolConLosPibes)
        val solicitud2 = Solicitud(solicitante = usuarioI, evento = voleibolALaTarde)
        val solicitud3 = Solicitud(solicitante = usuarioC, evento = basquet3Vs3)
        val solicitud4 = Solicitud(solicitante = usuarioG, evento = futbolConLosPibes)
        val solicitud5 = Solicitud(solicitante = usuarioH, evento = tenisSingles)
        val solicitud6 = Solicitud(solicitante = usuarioJ, evento = voleibolALaTarde)
        val solicitud7 = Solicitud(solicitante = usuarioE, evento = tenisSingles)
        val solicitud8 = Solicitud(solicitante = usuarioF, evento = pingPongEn4Dias)
        val solicitud9 = Solicitud(solicitante = usuarioA, evento = futbolConLosPibes)
        val solicitud11 = Solicitud(solicitante = usuarioC, evento = ajedrezEnLaPlaza)
        val solicitud12 = Solicitud(solicitante = usuarioD, evento = voleibolALaTarde)
        val solicitud14 = Solicitud(solicitante = usuarioH, evento = futbolConLosPibes)
        val solicitud15 = Solicitud(solicitante = usuarioI, evento = basquet3Vs3)
        val solicitud16 = Solicitud(solicitante = usuarioJ, evento = tenisSingles)
        val solicitud17 = Solicitud(solicitante = usuarioE, evento = voleibolALaTarde)
        val solicitud18 = Solicitud(solicitante = usuarioF, evento = ajedrezEnLaPlaza)
        val solicitud19 = Solicitud(solicitante = usuarioA, evento = pingPongEn4Dias)
        val solicitud21 = Solicitud(solicitante = usuarioC, evento = tenisSingles)
        val solicitud22 = Solicitud(solicitante = usuarioD, evento = basquet3Vs3)
        val solicitud23 = Solicitud(solicitante = usuarioG, evento = futbolConLosPibes)
        val solicitud24 = Solicitud(solicitante = usuarioH, evento = ajedrezEnLaPlaza)
        val solicitud25 = Solicitud(solicitante = usuarioI, evento = voleibolALaTarde)
        val solicitud26 = Solicitud(solicitante = usuarioJ, evento = pingPongEn4Dias)
        val solicitudAceptada1 = Solicitud(solicitante = usuarioD, evento = basquet3Vs3)
        solicitudAceptada1.responderSolicitud(true)
        val solicitudAceptada2 = Solicitud(solicitante = usuarioA, evento = ajedrezEnLaPlaza)
        solicitudAceptada2.responderSolicitud(true)
        val solicitudAceptada3 = Solicitud(solicitante = usuarioF, evento = eventoTerminado)
        solicitudAceptada3.estado = Estado.ACEPTADA
        val solicitudAceptada4 = Solicitud(solicitante = usuarioB, evento = eventoTerminado)
        solicitudAceptada4.estado = Estado.ACEPTADA
        val solicitudAceptada5 = Solicitud(solicitante = usuarioF, evento = basquet3Vs3)
        solicitudAceptada5.responderSolicitud(true)
        val solicitudAceptada6 = Solicitud(solicitante = usuarioC, evento = ajedrezEnLaPlaza)
        solicitudAceptada6.responderSolicitud(true)
        val solicitudAceptada7 = Solicitud(solicitante = usuarioG, evento = ajedrezEnLaPlaza)
        solicitudAceptada7.responderSolicitud(true)
        val solicitudAceptada8 = Solicitud(solicitante = usuarioH, evento = pingPongEn4Dias)
        solicitudAceptada8.responderSolicitud(true)
        val solicitudAceptada9 = Solicitud(solicitante = usuarioE, evento = basquet3Vs3)
        solicitudAceptada9.responderSolicitud(true)
        val solicitudAceptada10 = Solicitud(solicitante = usuarioF, evento = futbolConLosPibes)
        solicitudAceptada10.responderSolicitud(true)
        val solicitudAceptada11 = Solicitud(solicitante = usuarioA, evento = ajedrezEnLaPlaza)
        solicitudAceptada11.responderSolicitud(true)
        val solicitudAceptada12 = Solicitud(solicitante = usuarioB, evento = voleibolALaTarde)
        solicitudAceptada12.responderSolicitud(true)
        val solicitudAceptada13 = Solicitud(solicitante = usuarioC, evento = pingPongEn4Dias)
        solicitudAceptada13.responderSolicitud(true)
        val solicitudAceptada15 = Solicitud(solicitante = usuarioG, evento = tenisSingles)
        solicitudAceptada15.responderSolicitud(true)
        val solicitudAceptada16 = Solicitud(solicitante = usuarioH, evento = basquet3Vs3)
        solicitudAceptada16.responderSolicitud(true)
        val solicitudAceptada17 = Solicitud(solicitante = usuarioI, evento = futbolConLosPibes)
        solicitudAceptada17.responderSolicitud(true)
        val solicitudAceptada19 = Solicitud(solicitante = usuarioE, evento = voleibolALaTarde)
        solicitudAceptada19.responderSolicitud(true)

        arrayOf(
            solicitud1, solicitud2, solicitud3, solicitud4, solicitud5, solicitud6, solicitud7, solicitud8, solicitud9, solicitud11, solicitud12, solicitud14, solicitud15, solicitud16, solicitud17, solicitud18,
            solicitud19, solicitud21, solicitud22, solicitud23, solicitud24, solicitud25, solicitud26,
            solicitudAceptada1, solicitudAceptada2, solicitudAceptada3, solicitudAceptada4, solicitudAceptada5,
            solicitudAceptada6, solicitudAceptada7, solicitudAceptada8, solicitudAceptada9, solicitudAceptada10,
            solicitudAceptada11, solicitudAceptada12, solicitudAceptada13, solicitudAceptada15, solicitudAceptada16,
            solicitudAceptada17, solicitudAceptada19
        ).forEach { solicitudRepository.save(it) }

        //OPINIONES
        opinionService.crearOpinion(5, "Un capo, re buena onda", usuarioF.id, usuarioC.id)

        //REPORTES
        opinionService.reportarOpinion(opinionService.getOpinion(1).id, usuarioB.id)

    }

    fun clearMongoRepositories(){
        mensajeRepository.deleteAll()
    }

}
