package com.example.demo.repository

import com.example.demo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.crypto.password.PasswordEncoder

interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): User?
}