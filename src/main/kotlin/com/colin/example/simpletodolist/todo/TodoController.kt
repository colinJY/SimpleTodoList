package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.dto.SelectTodoResponseDto
import com.colin.example.simpletodolist.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TodoController(val todoService: TodoService) {

    @PostMapping("/todo")
    fun insert(
            @RequestBody @Valid insertTodoRequestDto: InsertTodoRequestDto
    ) : ResponseEntity<InsertTodoResponseDto> {

        val insertedTodo = todoService.insertTodo(insertTodoRequestDto)

        return ResponseEntity<InsertTodoResponseDto>(insertedTodo, HttpStatus.OK)
    }

    @GetMapping("/todo/{id}")
    fun select(
            @PathVariable id: Long
    ) : ResponseEntity<SelectTodoResponseDto> {

        val selectedTodo = todoService.selectTodo(id)

        return ResponseEntity<SelectTodoResponseDto>(selectedTodo, HttpStatus.OK)
    }
}