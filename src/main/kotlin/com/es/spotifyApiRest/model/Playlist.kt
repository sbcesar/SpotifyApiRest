package com.es.spotifyApiRest.model

import jakarta.persistence.*

@Entity
@Table(name = "playlists")
data class Playlist(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_playlist")
    val idPlaylist: Int,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "playlist_canciones",
        joinColumns = [JoinColumn(name = "id_playlist", referencedColumnName = "id_playlist")],
        inverseJoinColumns = [JoinColumn(name = "id_cancion", referencedColumnName = "id_cancion")]
    )
    val cancion: List<Cancion>,

    @Column(name = "titulo", nullable = false)
    val titulo: String,

    @Column(name = "breve_descripcion", nullable = false)
    val breveDescripcion: String,

    @Column(name = "total_canciones", nullable = false)
    val totalCanciones: Int,

    @Column(name = "duracion_total", nullable = false)
    val duracionTotal: Int
)