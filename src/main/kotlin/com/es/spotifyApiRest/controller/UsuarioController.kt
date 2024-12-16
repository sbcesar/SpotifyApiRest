package com.es.spotifyApiRest.controller

import com.es.spotifyApiRest.exceptions.errors.ValidationException
import com.es.spotifyApiRest.model.Usuario
import com.es.spotifyApiRest.service.TokenService
import com.es.spotifyApiRest.service.UsuarioService
import com.es.spotifyApiRest.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    val utils = Utils()

    @GetMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any>? {
        val authentication: Authentication

        try {
            authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(usuario.username, usuario.password))
        } catch (e: AuthenticationException) {
            return ResponseEntity(mapOf("mensaje" to "Credenciales incorrectas"), HttpStatus.UNAUTHORIZED)
        }

        var token = ""
        token = tokenService.generarToken(authentication)

        return ResponseEntity(mapOf("token" to token), HttpStatus.CREATED)
    }

    @PostMapping("/register")
    fun register(@RequestBody newUsuario: Usuario): ResponseEntity<Usuario?> {
        val usuario = usuarioService.registerUsuario(newUsuario)

        return ResponseEntity(usuario, HttpStatus.CREATED)
    }

    @GetMapping("/obtenerUsuarios")
    fun getAllUsers(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioService.getAllUsers()

        return if (usuarios.isNotEmpty()) {
            ResponseEntity(usuarios, HttpStatus.OK)
        } else {
            ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping("/obtenerUsuarios/{id}")
    fun getUsuarioById(@PathVariable id: String?): ResponseEntity<Usuario> {
        if (id.isNullOrBlank()) throw ValidationException("User ID is required")

        val usuario = usuarioService.getUserById(id)

        if (usuario != null) return ResponseEntity(usuario, HttpStatus.OK)

        return ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PutMapping("/updateUsuario/{id}")
    fun updateUsuario(@PathVariable id: String?, @RequestBody usuario: Usuario?): ResponseEntity<Usuario> {
        if (id.isNullOrBlank()) throw ValidationException("User ID is required")

        if (usuario == null) throw ValidationException("User is required")

        utils.validateUsuario(usuario)

        usuarioService.getUserById(id) ?: return ResponseEntity(null, HttpStatus.NOT_FOUND)

        try {
            val usuarioUpdated = usuarioService.updateUser(usuario)
            return ResponseEntity(usuarioUpdated, HttpStatus.OK)
        } catch (e: Exception) {
            throw ValidationException("Error updating user")
        }
    }

    @DeleteMapping("/borrarUsuario/{id}")
    fun deleteUsuario(@PathVariable id: String?): ResponseEntity<Usuario?> {
        if (id.isNullOrBlank()) throw ValidationException("User ID is required")

        usuarioService.deleteUser(id)
        return ResponseEntity(null, HttpStatus.OK)
    }
}