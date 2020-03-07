package com.colin.example.simpletodolist.todo.service

import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import org.springframework.stereotype.Service

@Service
class TodoService {
    fun insertPost(insertTodoRequestDto: InsertTodoRequestDto) : InsertTodoResponseDto {

        return InsertTodoResponseDto(0)
    }
}