package com.es.spotifyApiRest.service

import com.es.spotifyApiRest.exceptions.errors.NotFoundException
import com.es.spotifyApiRest.model.Cancion
import com.es.spotifyApiRest.repository.CancionRepository
import com.es.spotifyApiRest.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CancionService {

    @Autowired
    private lateinit var cancionRepository: CancionRepository

    val utils = Utils()

    fun getAllCanciones(): List<Cancion> {
        return cancionRepository.findAll()
    }

    fun getCancionById(idCancion: String): Cancion? {
        val id = utils.validateId(idCancion)

        val cancion = cancionRepository.findById(id.toLong()).orElse(null)
        return cancion ?: throw NotFoundException("Song with ID $id not found")
    }

    fun createCancion(cancion: Cancion): Cancion {
        utils.validateCancion(cancion)
        return cancionRepository.save(cancion)
    }

    fun updateCancion(cancion: Cancion): Cancion {
        if (!cancionRepository.existsById(cancion.idCancion.toLong())) {
            throw NotFoundException("Song with ID ${cancion.idCancion} not found")
        }

        utils.validateCancion(cancion)

        return cancionRepository.save(cancion)
    }

    fun deleteCancion(idCancion: String) {
        val id = utils.validateId(idCancion)

        if (!cancionRepository.existsById(id.toLong())){
            throw NotFoundException("Song with ID $id not found")
        }

        cancionRepository.deleteById(id.toLong())
    }

}