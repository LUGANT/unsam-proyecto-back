package grupo5yomesumo.springboot.domain

import jakarta.persistence.*

@Entity
data class Opinion(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)  var id: Long = 0,
    @Column var puntaje: Int = 0,
    @Column(length = 200) var comentario: String = "",
    @ManyToOne var usuarioOpinado: Usuario = Usuario(),
    @ManyToOne var usuarioOpinante : Usuario = Usuario()
)
