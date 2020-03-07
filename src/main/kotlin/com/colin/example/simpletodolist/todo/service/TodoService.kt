package com.colin.example.simpletodolist.todo.service

import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(private val todosRepository: TodosRepository) {

    @Transactional
    fun insertTodo(insertTodoRequestDto: InsertTodoRequestDto) : InsertTodoResponseDto {

        val savedTodo = todosRepository.save(insertTodoRequestDto.toEntity())
        return InsertTodoResponseDto(savedTodo.id)
    }
}