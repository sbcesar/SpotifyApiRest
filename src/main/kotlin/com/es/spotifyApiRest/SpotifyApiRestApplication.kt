package com.es.spotifyApiRest

import com.es.spotifyApiRest.security.RsaKeysProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RsaKeysProperties::class)
class SpotifyApiRestApplication

fun main(args: Array<String>) {
	runApplication<SpotifyApiRestApplication>(*args)
}
