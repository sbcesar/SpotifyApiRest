package com.es.spotifyApiRest.model

data class Usuario(
    val idUsuario: Int,
    val username: String,
    val password: String,
    val rol: String
)