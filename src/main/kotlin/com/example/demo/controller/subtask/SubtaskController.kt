package com.example.demo.controller.subtask

import com.example.demo.model.*
import com.example.demo.service.SubtaskService
import com.example.demo.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/subtask")
class SubtaskController(
        private val taskService: TaskService,
        private val subtaskService: SubtaskService
) {

    //просмотр подзадач
    @GetMapping
    fun getSubtasks(): List<Subtask> = subtaskService.findAll()

    //просмотр подзадачи по id
    @GetMapping("/{id}")
    fun getSubtaskById(@PathVariable id: Int): Optional<Subtask> {
        val subtask = subtaskService.findById(id)

        if (!subtask.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        return subtask
    }

    //создание подзадачи
    @PostMapping
    fun createSubtask(@RequestBody subtask: CreateSubtask): ResponseEntity<Subtask> {
        val task = taskService.findById(subtask.taskId)

        if (!task.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val newSubtask = subtaskService.create(
                Subtask(
                        taskId = subtask.taskId,
                        description = subtask.description,
                        dueDate = subtask.dueDate,
                        additionalInfo = subtask.additionalInfo
                )
        )

        return ResponseEntity.ok(newSubtask)
    }

    //редактирование подзадачи по id
    @PutMapping("/{id}")
    fun updateSubtaskById(@PathVariable id: Int, @RequestBody subtask: UpdateSubtask): ResponseEntity<Subtask> {
        val fullSubtask = subtaskService.findById(id)

        if (!fullSubtask.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val fullSubtaskObj = fullSubtask.get()
        fullSubtaskObj.description = subtask.description
        fullSubtaskObj.status = subtask.status
        fullSubtaskObj.dueDate = subtask.dueDate
        fullSubtaskObj.additionalInfo = subtask.additionalInfo

        val updatedTask = subtaskService.update(fullSubtaskObj)
        return ResponseEntity.ok(updatedTask)
    }

    // удаление подзадачи по id
    @DeleteMapping("/{id}")
    fun deleteSubtaskById(@PathVariable id: Int): ResponseEntity<Void> {
        val task = subtaskService.findById(id)

        if (!task.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        subtaskService.delete(id)
        return ResponseEntity.noContent().build()
    }

}