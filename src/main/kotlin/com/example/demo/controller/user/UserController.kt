package com.example.demo.controller.user

import com.example.demo.model.*
import com.example.demo.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = ["http://localhost:3000"])
class UserController(
        private val userService: UserService
) {
    @PostMapping
    fun create(@RequestBody user: CreateUser): ResponseEntity<GetUser> {
        println("TEST")
        val newUser = userService.create(
                User(
                        username = user.username,
                        password = user.password,
                        fio = user.fio,
                        phone = user.phone,
                        role = user.role
                )
        )

        var user = GetUser(
                id = newUser.id,
                username = newUser.username,
                fio = newUser.fio,
                phone = newUser.phone,
                role = newUser.role
        )

        return ResponseEntity.ok(user)
    }

    @GetMapping
    fun get(@AuthenticationPrincipal currentUser: UserDetails): ResponseEntity<GetUser> {
        var fullUser = userService.findByUsername(currentUser.username)
        var user = GetUser(
                id = fullUser!!.id,
                username = fullUser.username,
                fio = fullUser.fio,
                phone = fullUser.phone,
                role = fullUser.role
        )
        return ResponseEntity.ok(user)
    }
}