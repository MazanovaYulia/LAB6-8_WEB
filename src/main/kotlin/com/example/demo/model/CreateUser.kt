package com.example.demo.model

data class CreateUser(
        var username: String,
        var password: String,
        var fio: String,
        var phone: String,
        var role: Role = Role.ADMIN
)
