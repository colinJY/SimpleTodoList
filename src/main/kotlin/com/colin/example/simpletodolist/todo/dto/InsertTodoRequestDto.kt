package com.colin.example.simpletodolist.todo.dto

import com.colin.example.simpletodolist.todo.domain.Todos

data class InsertTodoRequestDto(
        val title: String,
        val content: String
) {
    fun toEntity(title: String, content: String): Todos = Todos(title, content)
}
