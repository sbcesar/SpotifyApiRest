package com.es.spotifyApiRest.service

import com.es.spotifyApiRest.exceptions.errors.ConflictException
import com.es.spotifyApiRest.exceptions.errors.NotFoundException
import com.es.spotifyApiRest.model.Cancion
import com.es.spotifyApiRest.model.Playlist
import com.es.spotifyApiRest.repository.CancionRepository
import com.es.spotifyApiRest.repository.PlaylistRepository
import com.es.spotifyApiRest.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlaylistService {

    @Autowired
    private lateinit var playlistRepository: PlaylistRepository

    @Autowired
    private lateinit var cancionRepository: CancionRepository

    private var utils = Utils()

    // Obtiene todas las playlist
    fun getAllPlaylists(): List<Playlist> {
        return playlistRepository.findAll()
    }

    // Obtiene la playlist por id
    fun getPlaylistById(idPlaylist: String): Playlist? {

        val id = utils.validateId(idPlaylist)

        val playlist = playlistRepository.findById(id.toLong()).orElse(null)
        return playlist ?: throw NotFoundException("Playlist with ID $id not found")
    }

    // Crea una nueva playlist
    fun createPlaylist(playlist: Playlist): Playlist {
        utils.validatePlaylist(playlist)

        return playlistRepository.save(playlist)
    }

    // Actualiza una playlist
    fun updatePlaylist(playlist: Playlist): Playlist {
        if (!playlistRepository.existsById(playlist.idPlaylist.toLong())) {
            throw NotFoundException("Playlist with ID ${playlist.idPlaylist} not found")
        }

        utils.validatePlaylist(playlist)

        return playlistRepository.save(playlist)
    }

    // Borra una playlist
    fun deletePlaylist(idPlaylist: String) {
        val id = utils.validateId(idPlaylist)

        if (!playlistRepository.existsById(id.toLong())) {
            throw NotFoundException("Playlist with ID $id not found")
        }
        playlistRepository.deleteById(id.toLong())
    }

    // Añade una cancion a la playlist (update)
    fun addSongInPlaylist(idPlaylist: String, cancion: Cancion): Playlist {
        val id = utils.validateId(idPlaylist)

        utils.validateCancion(cancion)

        if (!playlistRepository.existsById(id.toLong())) {
            throw NotFoundException("Playlist with ID $id not found")
        }

        val playlist = playlistRepository.findById(id.toLong()).get()

        if (playlist.cancion.contains(cancion)) {
            throw ConflictException("Sing already exists in the playlist")
        }

        // Si la cancion es nueva la guarda
        if (cancion.idCancion == 0) {
            cancionRepository.save(cancion)
        }

        playlist.cancion.add(cancion)

        val playlistUpdated = playlistRepository.save(playlist)

        return playlistUpdated
    }

    // Quita una cancion de la playlist
    fun deleteSongFromPlaylist(idPlaylist: String, idCancion: String): Playlist {
        val idPlaylistVerified = utils.validateId(idPlaylist)

        val idCancionVerified = utils.validateId(idCancion)

        if (!playlistRepository.existsById(idPlaylistVerified.toLong())) {
            throw NotFoundException("Playlist with ID $idPlaylistVerified not found")
        }

        val playlist = playlistRepository.findById(idPlaylistVerified.toLong()).get()

        val cancion = playlist.cancion.find { it.idCancion == idCancionVerified }

        if (!playlist.cancion.contains(cancion)) {
            throw NotFoundException("Song with ID $idCancionVerified not found in the playlist with ID $idPlaylistVerified")
        }

        playlist.cancion.remove(cancion)

        playlistRepository.save(playlist)

        return playlist
    }

    // Busca una cancion específica de la playlist por título
    fun searchCancionInPlaylist(idPlaylist: String, titulo: String): Cancion? {
        val id = utils.validateId(idPlaylist)

        if (!playlistRepository.existsById(id.toLong())) {
            throw NotFoundException("Playlist with ID $id not found")
        }

        val playlist = playlistRepository.findById(id.toLong()).get()

        val cancion = playlist.cancion.find { it.titulo == titulo }

        return cancion
    }

    // Calcula la duracion total de todas las canciones de la playlist
    fun calculateDuracionTotal(idPlaylist: String): Int {
        val id = utils.validateId(idPlaylist)

        if (!playlistRepository.existsById(id.toLong())) {
            throw NotFoundException("Playlist with ID $id not found")
        }

        val playlist = playlistRepository.findById(id.toLong()).get()

        return playlist.cancion.sumOf { it.duracion }
    }
}