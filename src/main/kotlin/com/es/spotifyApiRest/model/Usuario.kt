package com.es.spotifyApiRest.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    val idUsuario: Int? = null,

    @Column(name = "username", nullable = false, unique = true, length = 50)
    val username: String? = null,

    @Column(name = "password", nullable = false, length = 255)
    val password: String? = null,

    @Column(name = "role", nullable = false, length = 10)
    val rol: String? = null
)


