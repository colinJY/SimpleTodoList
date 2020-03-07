package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.dto.InsertPostRequestDto
import com.colin.example.simpletodolist.todo.service.TodoService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(val todoService: TodoService) {

    @PostMapping("/todo")
    fun insert(insertPostRequestDto: InsertPostRequestDto){

        todoService.insertPost(insertPostRequestDto)
    }
}