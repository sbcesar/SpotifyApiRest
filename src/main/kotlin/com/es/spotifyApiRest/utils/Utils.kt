package com.es.spotifyApiRest.utils

import com.es.spotifyApiRest.exceptions.errors.ValidationException
import com.es.spotifyApiRest.model.Cancion
import com.es.spotifyApiRest.model.Playlist
import java.time.LocalDate

class Utils {

    fun validateId(id: String): Int {
         try {
            return id.toInt()
        } catch (e: Exception) {
            throw ValidationException("ID must be an integer.")
        }
    }

    fun validatePlaylist(playlist: Playlist){
        if (playlist.titulo.isBlank()) throw ValidationException("El campo (titulo) no puede estar vacio.")
        if (playlist.breveDescripcion.isBlank()) throw ValidationException("El campo (breve descripcion) no puede estar vacio.")
        if (playlist.totalCanciones < 0) throw ValidationException("El campo (total canciones) no puede ser negativo.")
        if (playlist.duracionTotal < 0) throw ValidationException("El campo (duracion total) no puede ser negativo.")
    }

    fun validateCancion(cancion: Cancion) {
        if (cancion.titulo.isBlank()) throw ValidationException("El campo titulo no puede estar vacio.")
        if (cancion.artista.isBlank()) throw ValidationException("El campo (artista) no puede estar vacio.")
        if (cancion.album.isBlank()) throw ValidationException("El campo (album) no puede estar vacio.")
        if (cancion.fechaPublicacion.isAfter(LocalDate.now())) throw ValidationException("El campo (fecha publicacion) no puede ser posterior a la fecha actual.")
        if (cancion.duracion < 0) throw ValidationException("El campo (duracion) no puede ser negativo.")
    }

}