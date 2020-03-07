package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TodoController(val todoService: TodoService) {

    @PostMapping("/todo")
    fun insert(
            @RequestBody @Valid insertTodoRequestDto: InsertTodoRequestDto
    ) : ResponseEntity<InsertTodoResponseDto> {

        val insertTodo = todoService.insertTodo(insertTodoRequestDto)

        return ResponseEntity<InsertTodoResponseDto>(insertTodo, HttpStatus.OK)
    }
}