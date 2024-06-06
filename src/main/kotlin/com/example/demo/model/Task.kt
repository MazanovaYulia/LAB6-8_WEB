package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "tasks")
data class Task(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = -1,
        var description: String = "",
        var status: String = "ACTIVE",
        @Column(name = "due_date")
        var dueDate: String = "",
        @Column(name = "additional_info")
        var additionalInfo: String = ""
)
