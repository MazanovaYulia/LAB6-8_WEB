package com.example.demo.model

data class GetTask(
        val id: Int,
        val description: String,
        var status: String,
        var dueDate: String,
        var additionalInfo: String,
        var subtasks: List<Subtask> = emptyList()
)
