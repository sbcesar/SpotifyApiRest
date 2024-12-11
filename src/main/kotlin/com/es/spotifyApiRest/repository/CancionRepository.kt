package com.es.spotifyApiRest.repository

import com.es.spotifyApiRest.model.Cancion
import org.springframework.data.jpa.repository.JpaRepository

interface CancionRepository : JpaRepository<Cancion, Long>