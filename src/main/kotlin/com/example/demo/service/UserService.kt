package com.example.demo.service

import com.example.demo.config.JwtProperties
import com.example.demo.config.SHA256PasswordEncoder
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.util.Date

@Service
class UserService(
        jwtProperties: JwtProperties,
        private val userRepository: UserRepository
) {

    private val secretKey = Keys.hmacShaKeyFor(
            jwtProperties.key.toByteArray()
    )

    fun create(user: User): User {
        val passwordEncoder = SHA256PasswordEncoder()
        val hashedPassword = passwordEncoder.encode(user.password)

        user.password = hashedPassword
        return userRepository.save(user)
    }

    fun findByUsername(username: String) = userRepository.findByUsername(username)

    fun generateJwt(
            userDetails: UserDetails,
            expirationDate: Date,
            additionalClaims: Map<String, Any> = emptyMap()
    ): String =
            Jwts.builder()
                    .claims()
                    .subject(userDetails.username)
                    .issuedAt(Date(System.currentTimeMillis()))
                    .expiration(expirationDate)
                    .add(additionalClaims)
                    .and()
                    .signWith(secretKey)
                    .compact()

    fun extractUsernameFromJwt(token: String): String? =
            getJwtClaims(token)
                    .subject

    fun isJwtExpired(token: String): Boolean =
            getJwtClaims(token)
                    .expiration
                    .before(Date(System.currentTimeMillis()))

    fun isJwtValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsernameFromJwt(token)

        return userDetails.username == username && !isJwtExpired(token)
    }

    private fun getJwtClaims(token: String): Claims {
        val parser = Jwts.parser()
                .verifyWith(secretKey)
                .build()

        return parser.parseSignedClaims(token)
                .payload
    }
}