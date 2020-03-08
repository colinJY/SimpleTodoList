package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.dto.SelectTodoListResponseDto
import com.colin.example.simpletodolist.todo.dto.SelectTodoResponseDto
import com.colin.example.simpletodolist.todo.service.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/todos")
class TodoController(val todoService: TodoService) {

    @PostMapping
    fun insert(
            @RequestBody @Valid insertTodoRequestDto: InsertTodoRequestDto
    ) : ResponseEntity<InsertTodoResponseDto> {

        val insertedTodo = todoService.insertTodo(insertTodoRequestDto)

        return ResponseEntity.ok(insertedTodo)
    }

    @GetMapping("/{id}")
    fun select(@PathVariable id: Long) : ResponseEntity<SelectTodoResponseDto> {

        val selectedTodo = todoService.selectTodo(id)

        return ResponseEntity.ok(selectedTodo)
    }

    @GetMapping
    fun selectList(
            @PageableDefault(size = 10, page = 0) pageable: Pageable
    ) : ResponseEntity<Page<SelectTodoListResponseDto>> {

        val selectedTodoList = todoService.selectTodoList(pageable)

        return ResponseEntity.ok(selectedTodoList)
    }
}