package com.es.spotifyApiRest.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "canciones")
data class Cancion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancion")
    val idCancion: Int,

    @Column(name = "titulo", nullable = false, length = 100)
    val titulo: String,

    @Column(name = "artista", nullable = false, length = 100)
    val artista: String,

    @Column(name = "album", nullable = false,length = 100)
    val album: String,

    @Column(name = "fecha_publicacion", nullable = false)
    val fechaPublicacion: LocalDate,

    @Column(name = "duracion", nullable = false)
    val duracion: Int,

    @ManyToMany(mappedBy = "cancion", fetch = FetchType.LAZY)
    @JsonBackReference
    val playlist: MutableList<Playlist> = mutableListOf()
)