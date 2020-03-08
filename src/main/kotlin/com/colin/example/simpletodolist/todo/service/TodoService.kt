package com.colin.example.simpletodolist.todo.service

import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.dto.SelectTodoListResponseDto
import com.colin.example.simpletodolist.todo.dto.SelectTodoResponseDto
import com.colin.example.simpletodolist.todo.exception.TodosNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(private val todosRepository: TodosRepository) {

    @Transactional
    fun insertTodo(insertTodoRequestDto: InsertTodoRequestDto) : InsertTodoResponseDto {

        val savedTodo = todosRepository.save(insertTodoRequestDto.toEntity())
        return InsertTodoResponseDto(savedTodo.id)
    }

    fun selectTodo(id: Long): SelectTodoResponseDto {

        val selectedTodo = todosRepository.findById(id)
                .orElseThrow { throw TodosNotFoundException("Todo내역에서 존재하지 않습니다.") }

        return SelectTodoResponseDto(selectedTodo)
    }

    fun selectTodoList(pageable: Pageable): Page<SelectTodoListResponseDto> {

        return todosRepository.findAll(pageable).map {
            SelectTodoListResponseDto(it)
        }
    }
}