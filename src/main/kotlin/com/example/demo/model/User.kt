package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = -1,
        val username: String = "",
        var password: String = "",
        var fio: String = "",
        var phone: String = "",
        var role: Role = Role.ADMIN
)

enum class Role {
    USER, ADMIN
}
