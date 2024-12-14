package com.es.spotifyApiRest.controller

import com.es.spotifyApiRest.exceptions.errors.ValidationException
import com.es.spotifyApiRest.model.Cancion
import com.es.spotifyApiRest.service.CancionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/canciones")
class CancionController {

    @Autowired
    private lateinit var cancionService: CancionService

    @GetMapping("/obtenerCanciones")
    fun getAllCanciones(): ResponseEntity<List<Cancion>> {
        val canciones = cancionService.getAllCanciones()

        return if (canciones.isNotEmpty()) {
            ResponseEntity(canciones, HttpStatus.OK)
        } else {
            ResponseEntity(emptyList(), HttpStatus.NO_CONTENT)
        }
    }

    @GetMapping("/obtenerCanciones/{id}")
    fun getCancionById(@PathVariable id: String?): ResponseEntity<Cancion> {
        if (id.isNullOrBlank()) throw ValidationException("Song ID is required")

        val cancion = cancionService.getCancionById(id)

        if (cancion != null) {
            return ResponseEntity(cancion, HttpStatus.OK)
        }

        return ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping("/crearCancion")
    fun createCancion(@RequestBody cancion: Cancion?): ResponseEntity<Cancion> {
        if (cancion == null) throw ValidationException("Song is required")

        try {
            val cancionCreated = cancionService.createCancion(cancion)
            return ResponseEntity(cancionCreated, HttpStatus.CREATED)
        } catch (ex: Exception) {
            throw ValidationException("Error creating song")
        }
    }

    @PutMapping("/actualizarCancion/{id}")
    fun updateCancion(@PathVariable id: String?, @RequestBody cancion: Cancion?): ResponseEntity<Cancion> {
        if (id.isNullOrBlank()) throw ValidationException("Song ID is required")

        if (cancion == null) throw ValidationException("Song is required")

        cancionService.getCancionById(id) ?: return ResponseEntity(null, HttpStatus.NOT_FOUND)

        try {
            val cancionUpdated = cancionService.updateCancion(cancion)
            return ResponseEntity(cancionUpdated, HttpStatus.OK)
        } catch (ex: Exception) {
            throw ValidationException("Error updating song")
        }
    }

    @DeleteMapping("/borrarCancion/{id}")
    fun borrarCancion(@PathVariable id: String?): ResponseEntity<Cancion?> {
        if (id.isNullOrBlank()) throw ValidationException("Song ID is required")

        cancionService.deleteCancion(id)
        return ResponseEntity(null, HttpStatus.OK)
    }
}