package com.example.demo.controller.auth

import com.example.demo.model.*
import com.example.demo.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val authenticationService: AuthenticationService
) {

    @PostMapping
    fun login(@RequestBody authRequest: AuthenticationRequest) : AuthenticationResponse =
            authenticationService.authentication(authRequest)
}