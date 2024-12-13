package com.es.spotifyApiRest.exceptions.errors

class NotFoundException(mensaje: String) : Exception("Not found error (404) $mensaje") {
}