package com.es.spotifyApiRest.model

import java.time.LocalDate

data class Canciones(
    val idCancion: Int,
    val titulo: String,
    val artista: String,
    val album: String,
    val fechaPublicacion: LocalDate,
    val duracion: Int
)