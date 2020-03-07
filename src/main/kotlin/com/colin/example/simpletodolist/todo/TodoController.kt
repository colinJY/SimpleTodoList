package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.service.TodoService
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TodoController(val todoService: TodoService) {

    @PostMapping("/todo")
    fun insert(
            @RequestBody @Valid insertTodoRequestDto: InsertTodoRequestDto,
            errors: Errors
    ) : InsertTodoResponseDto{

        return todoService.insertPost(insertTodoRequestDto)
    }
}