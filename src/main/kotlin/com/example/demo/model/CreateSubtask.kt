package com.example.demo.model

data class CreateSubtask(
        var taskId: Int,
        var description: String = "",
        var dueDate: String,
        var additionalInfo: String = ""
)
