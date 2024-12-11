package com.es.spotifyApiRest.service

import com.es.spotifyApiRest.model.Cancion
import com.es.spotifyApiRest.repository.CancionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CancionService {

    @Autowired
    private lateinit var cancionRepository: CancionRepository

    // Obtiene todas las canciones (ADMIN y USER)
    fun getAllCanciones(): List<Cancion> {

    }

    // Obtiene cancion por id (ADMIN USER ARTIST)
    fun getCancionById(idCancion: String): Cancion? {

    }

    // Busca cancion por titulo (ADMIN USER)
    fun searchByTitulo(titulo: String): List<Cancion> {

    }

    // Busca cancion por artista (ADMIN USER)
    fun searchByArtista(artista: String): List<Cancion> {

    }

    // Crea una nueva cancion (ADMIN ARTIST)
    fun createCancion(nuevaCancion: Cancion): Cancion {

    }

    // Edita una cancion (ADMIN ARTIST)
    fun updateCancion(idCancion: String, nuevaCancion: Cancion): Cancion {

    }

    // Borra una cancion (ADMIN)
    fun deleteCancion(idCancion: String) {

    }
}