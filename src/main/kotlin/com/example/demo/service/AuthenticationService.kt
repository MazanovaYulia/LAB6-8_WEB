package com.example.demo.service

import com.example.demo.config.JwtProperties
import com.example.demo.model.AuthenticationRequest
import com.example.demo.model.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
        private val authManager: AuthenticationManager,
        private val userDetailsService: CustomUserDetailsService,
        private val userService: UserService,
        private val jwtProperties: JwtProperties
) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        authRequest.username,
                        authRequest.password
                )
        )
        val user = userDetailsService.loadUserByUsername(authRequest.username)
        val accessToken = userService.generateJwt(
                userDetails = user,
                expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

        return AuthenticationResponse(
                accessToken = accessToken
        )
    }
}