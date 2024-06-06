package com.example.demo.model

data class GetUser(
        val id: Int,
        val username: String,
        var fio: String,
        var phone: String,
        var role: Role
)
