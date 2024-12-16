package com.es.spotifyApiRest.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    var idUsuario: Int? = null,

    @Column(name = "username", nullable = false, unique = true, length = 50)
    var username: String? = null,

    @Column(name = "password", nullable = false, length = 255)
    var password: String? = null,

    @Column(name = "roles", length = 10)
    var roles: String? // USER, ADMIN, ARTIST
)


