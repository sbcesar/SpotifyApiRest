package com.es.spotifyApiRest.repository

import com.es.spotifyApiRest.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    fun findByUsername(username: String): Optional<Usuario>
}