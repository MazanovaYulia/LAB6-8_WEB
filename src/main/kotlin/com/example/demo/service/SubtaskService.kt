package com.example.demo.service

import com.example.demo.model.Subtask
import com.example.demo.model.Task
import com.example.demo.repository.SubtaskRepository
import com.example.demo.repository.TaskRepository
import org.springframework.stereotype.Service

@Service
class SubtaskService(
        private val subtaskRepository: SubtaskRepository
) {
    fun findAll() = subtaskRepository.findAll()
    fun findById(id: Int) = subtaskRepository.findById(id)
    fun findAllByTaskId(taskId: Int) = subtaskRepository.findByTaskId(taskId)
    fun create(task: Subtask) = subtaskRepository.save(task)
    fun update(task: Subtask) = subtaskRepository.save(task)
    fun delete(id: Int) = subtaskRepository.deleteById(id)
}