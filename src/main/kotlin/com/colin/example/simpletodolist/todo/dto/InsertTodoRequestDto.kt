package com.colin.example.simpletodolist.todo.dto

import com.colin.example.simpletodolist.todo.domain.Todos
import javax.validation.constraints.*

data class InsertTodoRequestDto(
        @get:NotBlank @get:Size(max = 200)
        val title: String,
        @get:NotBlank @get:Size(max = 2000)
        val content: String
) {
    fun toEntity(title: String, content: String): Todos = Todos(title, content)
}
