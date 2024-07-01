package grupo5yomesumo.springboot.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
data class Opinion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  var id: Long = 0,
    @Column var puntaje: Int = 0,
    @Column(length = 200) var comentario: String = "",
    @Column val fecha: LocalDate = LocalDate.now(),
    @ManyToOne var usuarioOpinado: Usuario = Usuario(),
    @ManyToOne var usuarioOpinante : Usuario = Usuario()
)

@Entity
data class Reporte(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long = 0,
    @ManyToOne val opinion: Opinion = Opinion(),
    @ManyToOne val reportador: Usuario = Usuario()
)