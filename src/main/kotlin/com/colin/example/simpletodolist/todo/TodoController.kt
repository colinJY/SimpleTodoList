package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.dto.*
import com.colin.example.simpletodolist.todo.service.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/todos")
class TodoController(val todoService: TodoService) {

    @PostMapping
    fun insert(
            @RequestBody @Valid insertTodoRequestDto: InsertTodoRequestDto
    ) : ResponseEntity<InsertTodoResponseDto> {

        val insertedTodo = todoService.insertTodo(insertTodoRequestDto)

        return ResponseEntity.created(URI.create("/todos")).body(insertedTodo)
    }

    @GetMapping("/{id}")
    fun select(@PathVariable id: Long) : ResponseEntity<SelectTodoResponseDto> {

        val selectedTodo = todoService.selectTodo(id)

        return ResponseEntity.ok(selectedTodo)
    }

    @GetMapping
    fun selectList(
            @PageableDefault(size = 20, page = 0) pageable: Pageable
    ): ResponseEntity<Page<SelectTodoListResponseDto>> {

        val selectedTodoList = todoService.selectTodoList(pageable)

        return ResponseEntity.ok(selectedTodoList)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updateTodoRequestDto: UpdateTodoRequestDto): ResponseEntity<UpdateTodoResponseDto> {

        val updatedTodo = todoService.updateTodo(id ,updateTodoRequestDto)

        return ResponseEntity.ok(updatedTodo)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {

        todoService.deleteTodo(id)

        return ResponseEntity.noContent().build()
    }

    @PutMapping
    fun updateTodoList(@RequestBody updateTodoListRequestDto: List<UpdateTodoListRequestDto>): ResponseEntity<Any> {

        todoService.updateTodoList(updateTodoListRequestDto)

        return ResponseEntity.noContent().build()
    }
}