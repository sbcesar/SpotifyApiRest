package com.es.spotifyApiRest.exceptions.errors

class ValidationException(message: String) : Exception("Validation error (400) $message") {
}