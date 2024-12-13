package com.es.spotifyApiRest.controller

import com.es.spotifyApiRest.exceptions.errors.ValidationException
import com.es.spotifyApiRest.model.Cancion
import com.es.spotifyApiRest.model.Playlist
import com.es.spotifyApiRest.service.PlaylistService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/playlists")
class PlaylistController {

    @Autowired
    private lateinit var playlistService: PlaylistService

    @GetMapping("/obtenerPlaylists")
    fun getAllPlaylists(): ResponseEntity<List<Playlist>> {
        val playlists = playlistService.getAllPlaylists()

        return if (playlists.isNotEmpty()) {
            ResponseEntity(playlists, HttpStatus.OK)
        } else {
            ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping("/obtenerPlaylist/{id}")
    fun getPlaylistById(@PathVariable id: String?): ResponseEntity<Playlist?> {
        if (id.isNullOrBlank()) throw ValidationException("Playlist ID is required")

        val playlist = playlistService.getPlaylistById(id)

        if (playlist != null) {
            return ResponseEntity(playlist, HttpStatus.OK)
        }

        return ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @GetMapping("/obtenerPlaylist/{id}/{titulo}")
    fun getPlaylistSongByTitle(@PathVariable id: String?, @PathVariable titulo: String?): ResponseEntity<Cancion?> {
        if (id.isNullOrBlank()) throw ValidationException("Playlist ID is required")

        if (titulo.isNullOrBlank()) throw ValidationException("Playlist title is required")

        val cancion = playlistService.searchCancionInPlaylist(id, titulo)

        if (cancion != null) return ResponseEntity(cancion, HttpStatus.OK)

        return ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping("/crearPlaylist")
    fun createPlaylist(@RequestBody playlist: Playlist?): ResponseEntity<Playlist> {
        if (playlist == null) throw ValidationException("Playlist is required")

        try {
            val playlistCreated = playlistService.createPlaylist(playlist)
            return ResponseEntity(playlistCreated, HttpStatus.CREATED)
        } catch (e: Exception) {
            throw ValidationException("Error creating playlist")
        }
    }

    @PutMapping("/actualizarPlaylist/{id}")
    fun updatePlaylist(@PathVariable id: String?, @RequestBody playlist: Playlist?): ResponseEntity<Playlist> {
        if (id.isNullOrBlank()) throw ValidationException("Playlist ID is required")

        if (playlist == null) throw ValidationException("Playlist is required")

        playlistService.getPlaylistById(id) ?: return ResponseEntity(null, HttpStatus.NOT_FOUND)

        try {
            val playlistUpdated = playlistService.updatePlaylist(playlist)
            return ResponseEntity(playlistUpdated, HttpStatus.OK)
        } catch (e: Exception) {
            throw ValidationException("Error updating playlist")
        }
    }

    @PutMapping("/agregarCancion/{id}")
    fun addCancionInPlaylist(@PathVariable id: String?, @RequestBody cancion: Cancion?): ResponseEntity<Playlist?> {
        if (id.isNullOrBlank()) throw ValidationException("Playlist ID is required")

        if (cancion == null) throw ValidationException("Song is required")

        playlistService.getPlaylistById(id) ?: return ResponseEntity(null, HttpStatus.NOT_FOUND)

        try {
            val playlistUpdated = playlistService.addSongInPlaylist(id, cancion)
            return ResponseEntity(playlistUpdated, HttpStatus.OK)
        } catch (e: Exception) {
            throw ValidationException("Error adding the song to the playlist")
        }
    }

    @DeleteMapping("/borrarPlaylist/{id}")
    fun deletePlaylist(@PathVariable id: String?): ResponseEntity<Playlist?> {
        if (id.isNullOrBlank()) throw ValidationException("Playlist ID is required")

        playlistService.deletePlaylist(id)
        return ResponseEntity(null, HttpStatus.OK)
    }

    @DeleteMapping("/borrarCancion/{id}")
    fun deleteSongFromPlaylist(@PathVariable id: String?, @RequestBody cancion: Cancion?): ResponseEntity<Playlist?> {
        if (id.isNullOrBlank()) throw ValidationException("Playlist ID is required")

        if (cancion == null) throw ValidationException("Cancion is required")

        playlistService.deleteSongFromPlaylist(id, cancion)
        return ResponseEntity(null, HttpStatus.OK)
    }
}