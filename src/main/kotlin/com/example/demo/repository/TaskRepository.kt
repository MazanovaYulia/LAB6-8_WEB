package com.example.demo.repository

import com.example.demo.model.Task
import org.springframework.data.jpa.repository.JpaRepository


interface TaskRepository : JpaRepository<Task, Int> {
}