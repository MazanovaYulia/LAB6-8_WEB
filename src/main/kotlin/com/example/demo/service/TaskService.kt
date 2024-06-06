package com.example.demo.service

import com.example.demo.model.Task
import com.example.demo.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(
        private val taskRepository: TaskRepository
) {
    fun findAll() = taskRepository.findAll()
    fun findById(id: Int) = taskRepository.findById(id)
    fun create(task: Task) = taskRepository.save(task)
    fun update(task: Task) = taskRepository.save(task)
    fun delete(id: Int) = taskRepository.deleteById(id)
}