package com.es.spotifyApiRest.service

import com.es.spotifyApiRest.model.Cancion
import com.es.spotifyApiRest.model.Playlist
import com.es.spotifyApiRest.repository.PlaylistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlaylistService {

    @Autowired
    private lateinit var playlistRepository: PlaylistRepository

    @Autowired
    private lateinit var cancionService: CancionService

    // Obtiene todas las playlist (ADMIN USER)
    fun getAllPlaylists(): List<Playlist> {

    }

    // Obtiene la playlist por id (ADMIN USER)
    fun getPlaylistById(id: String): Playlist? {

    }

    // Crea una nueva playlist
    fun createPlaylist(playlist: Playlist) {

    }

    fun updatePlaylist(playlist: Playlist) {

    }

    fun deletePlaylist(id: String) {

    }

    fun addCancionToPlaylist(idPlaylist: String, idCancion: String): Playlist {

    }

    fun removeCancionFromPlaylist(idPlaylist: String, idCancion: String) {

    }

    fun searchCancionInPlaylist(idPlaylist: String, titulo: String) {

    }

    fun calculateDuracionTotal(idPlaylist: String): Int {
        //playlist.cancion.sumOf { it.duracion }
    }

    // Obtiene todas las playlist del usuario del id especificado
    fun getPlaylistByUsername(idUsuario: String): List<Playlist> {

    }
}