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
        val basquet = Actividad(nombre = "Basquet", esGrupal = true)
        val futbol = Actividad(nombre = "Futbol", esGrupal = true)
        val tenisIndividual = Actividad(nombre = "Tenis individual", esGrupal = false)
        val tenisDobles = Actividad(nombre = "Tenis dobles", esGrupal = true)
        val handball = Actividad(nombre = "Handball", esGrupal = true)
        val voleibol = Actividad(nombre = "Voleibol", esGrupal = true)
        val voleibolPlaya = Actividad(nombre = "Voleibol de playa", esGrupal = true)
        val natacion = Actividad(nombre = "Natación", esGrupal = false)
        val ciclismo = Actividad(nombre = "Ciclismo", esGrupal = false)
        val ajedrez = Actividad(nombre = "Ajedrez", esGrupal = false)
        val rugby = Actividad(nombre = "Rugby", esGrupal = true)
        val pingPong = Actividad(nombre = "Ping Pong", esGrupal = false)
        val beisbol = Actividad(nombre = "Béisbol", esGrupal = true)
        val patinaje = Actividad(nombre = "Patinaje", esGrupal = false)

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
        val solicitudAceptada1 = Solicitud(solicitante = usuarioD, evento = basquet3Vs3)
        solicitudAceptada1.responderSolicitud(true)
        val solicitudAceptada2 = Solicitud(solicitante = usuarioA, evento = ajedrezEnLaPlaza)
        solicitudAceptada2.responderSolicitud(true)
        val solicitudAceptada3 = Solicitud(solicitante = usuarioF, evento = eventoTerminado)
        solicitudAceptada3.estado = Estado.ACEPTADA
        val solicitudAceptada4 = Solicitud(solicitante = usuarioB, evento = eventoTerminado)
        solicitudAceptada4.estado = Estado.ACEPTADA

        arrayOf(
            solicitud1, solicitud2, solicitud3, solicitudAceptada1, solicitudAceptada2, solicitudAceptada3, solicitudAceptada4
        ).forEach { solicitudRepository.save(it) }

        //OPINIONES
        opinionService.crearOpinion(5, "Un capo, re buena onda", usuarioF.id, usuarioC.id)
    }

    fun clearMongoRepositories(){
        mensajeRepository.deleteAll()
    }

}
