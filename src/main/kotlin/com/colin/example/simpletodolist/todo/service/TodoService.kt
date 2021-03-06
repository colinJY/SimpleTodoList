package com.colin.example.simpletodolist.todo.service

import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.dto.*
import com.colin.example.simpletodolist.todo.exception.TodosNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(private val todosRepository: TodosRepository) {

    @Transactional
    fun insertTodo(insertTodoRequestDto: InsertTodoRequestDto) : InsertTodoResponseDto {

        val savedTodo = todosRepository.save(insertTodoRequestDto.toEntity())
        return InsertTodoResponseDto(savedTodo)
    }

    @Transactional(readOnly = true)
    fun selectTodo(id: Long): SelectTodoResponseDto {

        val selectedTodo = todosRepository.findById(id)
                .orElseThrow { throw TodosNotFoundException("Todo 내역에서 존재하지 않습니다.") }

        return SelectTodoResponseDto(selectedTodo)
    }

    @Transactional(readOnly = true)
    fun selectTodoList(pageable: Pageable): Page<SelectTodoListResponseDto> {

        return todosRepository.findAll(pageable).map {
            SelectTodoListResponseDto(it)
        }
    }

    @Transactional
    fun updateTodo(id:Long, updateTodoRequestDto: UpdateTodoRequestDto): UpdateTodoResponseDto {

        val todo = todosRepository.findById(id).orElseThrow { throw TodosNotFoundException("수정할 Todo가 존재하지 않습니다.") }
        todo.update(content = updateTodoRequestDto.content)

        return UpdateTodoResponseDto(todo)
    }

    @Transactional
    fun deleteTodo(id: Long) {
        todosRepository.findById(id).orElseThrow { throw TodosNotFoundException("삭제할 Todo가 존재하지 않습니다.") }
        todosRepository.deleteById(id)
    }

    @Transactional
    fun updateTodoList(updateTodoListRequestDto: List<UpdateTodoListRequestDto>) {
        updateTodoListRequestDto.forEach { updateTodo(it.id, UpdateTodoRequestDto(it.content)) }
    }
}