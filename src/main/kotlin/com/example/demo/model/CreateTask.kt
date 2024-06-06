package com.example.demo.model

data class CreateTask(
        var description: String = "",
        var dueDate: String,
        var additionalInfo: String = ""
)
