package grupo5yomesumo.springboot.repository

import grupo5yomesumo.springboot.domain.exceptions.BusinessException
import grupo5yomesumo.springboot.domain.exceptions.NotFoundException
import org.springframework.stereotype.Repository

interface Entidad {
    var id: Long

    fun esNuevo(): Boolean = id == 0L

}

@Repository
class CrudRepo<T:Entidad> {
    val objetos: MutableList<T> = mutableListOf()
    var nextId = 1L

    fun save(objeto: T) {
        validarEsNuevo(objeto)
        asignarId(objeto)
        objetos.add(objeto)
    }

    fun asignarId(objeto: T){
        objeto.id = nextId++
    }

    fun validarEsNuevo(objeto: T) {
        if (!objeto.esNuevo()) {
            throw BusinessException("El elemento con id ${objeto.id} ya existe")
        }
    }

    fun delete(objeto: T) = objetos.remove(objeto)

    fun getById(id: Long): T = objetos.find { objeto -> objeto.id == id } ?: throw NotFoundException("No se ha encontrado el elemento con el id ${id}")

    fun update(objeto: T) {
        val objetoAActualizar = getById(objeto.id)
        val index = objetos.indexOf(objetoAActualizar)
        objetos[index] = objeto
    }

    fun findAll() = objetos

}