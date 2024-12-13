package com.es.spotifyApiRest.exceptions.errors

class ConflictException(message: String) : Exception("Conflict error (409) $message") {
}