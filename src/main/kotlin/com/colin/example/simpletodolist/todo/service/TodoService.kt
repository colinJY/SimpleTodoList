package com.colin.example.simpletodolist.todo.service

import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.dto.SelectTodoResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class TodoService(private val todosRepository: TodosRepository) {

    @Transactional
    fun insertTodo(insertTodoRequestDto: InsertTodoRequestDto) : InsertTodoResponseDto {

        val savedTodo = todosRepository.save(insertTodoRequestDto.toEntity())
        return InsertTodoResponseDto(savedTodo.id)
    }

    fun selectTodo(id: Long): SelectTodoResponseDto {

        val selectedTodo = todosRepository.findById(id)
                .orElseThrow { throw IllegalArgumentException("입력하신 ID에 존재하는 TODO가 없습니다.") }

        return SelectTodoResponseDto(selectedTodo)
    }
}