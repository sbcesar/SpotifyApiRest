package com.es.spotifyApiRest.service

import com.es.spotifyApiRest.exceptions.errors.NotFoundException
import com.es.spotifyApiRest.model.Usuario
import com.es.spotifyApiRest.repository.UsuarioRepository
import com.es.spotifyApiRest.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    val utils = Utils()

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = usuarioRepository.findByUsername(username!!).orElseThrow()

        val roles = listOf(SimpleGrantedAuthority("ROLE_${usuario.roles}"))

        return User.builder()
            .username(usuario.username)
            .password(usuario.password)
            .authorities(roles)
            .build()
    }

    fun registerUsuario(usuario: Usuario): Usuario? {

        if (usuario.username.isNullOrBlank()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username required")
        }

        if (usuarioRepository.findByUsername(usuario.username!!).isPresent) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Username already exists")
        }

        usuario.password = passwordEncoder.encode(usuario.password)

        return usuarioRepository.save(usuario)
    }

    fun getAllUsers(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun getUserById(idUsuario: String): Usuario? {
        val id = utils.validateId(idUsuario)

        val usuario = usuarioRepository.findById(id.toLong()).orElse(null)
        return usuario ?: throw NotFoundException("User with ID $id not found")
    }

    fun updateUser(usuario: Usuario): Usuario {
        if (!usuarioRepository.existsById(usuario.idUsuario!!.toLong())) {
            throw NotFoundException("User with ID ${usuario.idUsuario} not found")
        }

        return usuarioRepository.save(usuario)
    }

    fun deleteUser(idUsuario: String) {
        val id = utils.validateId(idUsuario)

        if (!usuarioRepository.existsById(id.toLong())) {
            throw NotFoundException("User with ID $id not found")
        }

        usuarioRepository.deleteById(id.toLong())
    }
}