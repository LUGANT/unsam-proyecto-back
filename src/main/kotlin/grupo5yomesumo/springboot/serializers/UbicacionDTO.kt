package grupo5yomesumo.springboot.serializers

import grupo5yomesumo.springboot.domain.Ubicacion

class UbicacionDTO(
    val nombreCompletoLugar: String,
    val barrio: String,
    val lat: Double,
    val lon: Double
) {

    constructor(ubicacion: Ubicacion) : this(
        nombreCompletoLugar = ubicacion.nombreLugar,
        barrio = ubicacion.barrio,
        lat = ubicacion.coordenadas.x,
        lon = ubicacion.coordenadas.y
    )
}