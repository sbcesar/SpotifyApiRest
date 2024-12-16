package com.es.spotifyApiRest.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    private lateinit var rsaKeys: RsaKeysProperties

    @Bean
    fun securityChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }    // Cross-Site Forgery
            .authorizeHttpRequests { auth -> auth

                // Registro
                .requestMatchers(HttpMethod.GET, "/usuarios/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios/register").permitAll()

                // Enpoints -> Usuario
                .requestMatchers(HttpMethod.GET, "/usuarios/obtenerUsuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/usuarios/obtenerUsuarios/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/usuarios/updateUsuario/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/usuarios/borrarUsuario/{id}").hasRole("ADMIN")

                // Endpoints -> Canciones
                .requestMatchers(HttpMethod.GET, "/canciones/obtenerCanciones").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.GET, "/canciones/obtenerCanciones/{id}").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.POST, "/canciones/crearCancion").hasAnyRole("ADMIN", "ARTIST")
                .requestMatchers(HttpMethod.PUT, "/canciones/actualizarCancion/{id}").hasAnyRole("ADMIN", "ARTIST")
                .requestMatchers(HttpMethod.DELETE, "/canciones/borrarCancion/{id}").hasRole("ADMIN")

                // Endpoints -> Playlist
                .requestMatchers(HttpMethod.GET, "/playlists/obtenerPlaylists").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.GET, "/playlists/obtenerPlaylist/{id}").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.GET, "/playlists/obtenerPlaylist/{id}/{titulo}").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.POST, "/playlists/crearPlaylist").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.PUT, "/playlists/actualizarPlaylist/{id}").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.PUT, "/playlists/agregarCancion/{id}").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.DELETE, "/playlists/borrarPlaylist/{id}").hasAnyRole("ADMIN", "USER", "ARTIST")
                .requestMatchers(HttpMethod.DELETE, "/playlists/borrarCancion/{idPlaylist}/{idCancion}").hasAnyRole("ADMIN", "USER", "ARTIST")

                .anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults())}
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration) : AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeys.publicKey).privateKey(rsaKeys.privateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey).build()
    }
}