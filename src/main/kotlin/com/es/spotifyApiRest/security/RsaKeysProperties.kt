package com.es.spotifyApiRest.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@ConfigurationProperties(prefix = "rsa")
data class RsaKeysProperties(
    var publicKey: RSAPublicKey,
    var privateKey: RSAPrivateKey
)