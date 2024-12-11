package com.es.spotifyApiRest.repository

import com.es.spotifyApiRest.model.Playlist
import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistRepository : JpaRepository<Playlist, Long> {
}