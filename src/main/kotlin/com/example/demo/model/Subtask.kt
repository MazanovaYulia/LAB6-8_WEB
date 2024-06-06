package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "subtasks")
data class Subtask(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = -1,
        @Column(name = "task_id")
        val taskId: Int = -1,
        var description: String = "",
        var status: String = "ACTIVE",
        @Column(name = "due_date")
        var dueDate: String = "",
        @Column(name = "additional_info")
        var additionalInfo: String = ""
)
