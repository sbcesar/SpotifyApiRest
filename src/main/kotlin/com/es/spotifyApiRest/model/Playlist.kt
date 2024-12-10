package com.es.spotifyApiRest.model

data class Playlist(
    val idPlaylist: Int,
    val cancion: Canciones,
    val titulo: String,
    val breveDescripcion: String,
    val totalCanciones: Int,
    val duracionTotal: Int
)