package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.service.TodoService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(val todoService: TodoService) {

    @PostMapping("/todo")
    fun insert(@RequestBody insertTodoRequestDto: InsertTodoRequestDto) : InsertTodoResponseDto{

        return todoService.insertPost(insertTodoRequestDto)
    }
}