package com.example.demo.controller.task

import com.example.demo.model.CreateTask
import com.example.demo.model.GetTask
import com.example.demo.model.Task
import com.example.demo.model.UpdateTask
import com.example.demo.service.SubtaskService
import com.example.demo.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/task")
class TaskController(
        private val taskService: TaskService,
        private val subtaskService: SubtaskService
) {

    //просмотр задач
    @GetMapping
    fun getTasks(): List<Task> = taskService.findAll()

    //просмотр задачи по id
    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Int): GetTask {
        val fullTask = taskService.findById(id)

        if (!fullTask.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val fullTaskObj = fullTask.get()
        val subtasks = subtaskService.findAllByTaskId(id)

        val task = GetTask(
                id = fullTaskObj.id,
                description = fullTaskObj.description,
                status = fullTaskObj.status,
                dueDate = fullTaskObj.dueDate,
                additionalInfo = fullTaskObj.additionalInfo,
                subtasks = subtasks
        )

        return task
    }

    //создание задачи
    @PostMapping
    fun createTask(@RequestBody task: CreateTask): ResponseEntity<Task> {
        val newTask = taskService.create(
                Task(
                        description = task.description,
                        dueDate = task.dueDate,
                        additionalInfo = task.additionalInfo
                )
        )

        return ResponseEntity.ok(newTask)
    }

    //редактирование задачи по id
    @PutMapping("/{id}")
    fun updateTaskById(@PathVariable id: Int, @RequestBody task: UpdateTask): ResponseEntity<Task> {
        val fullTask = taskService.findById(id)

        if (!fullTask.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val fullTaskObj = fullTask.get()
        fullTaskObj.description = task.description
        fullTaskObj.status = task.status
        fullTaskObj.dueDate = task.dueDate
        fullTaskObj.additionalInfo = task.additionalInfo

        val updatedTask = taskService.update(fullTaskObj)
        return ResponseEntity.ok(updatedTask)
    }

    // удаление задачи по id
    @DeleteMapping("/{id}")
    fun deleteTaskById(@PathVariable id: Int): ResponseEntity<Void> {
        val task = taskService.findById(id)

        if (!task.isPresent) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        taskService.delete(id)
        return ResponseEntity.noContent().build()
    }

}