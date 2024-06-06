package com.example.demo.repository

import com.example.demo.model.Subtask
import org.springframework.data.jpa.repository.JpaRepository


interface SubtaskRepository : JpaRepository<Subtask, Int> {
    fun findByTaskId(taskId: Int): List<Subtask>
}