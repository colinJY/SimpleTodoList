package com.colin.example.simpletodolist.todo.dto

import com.colin.example.simpletodolist.todo.domain.Todos

data class InsertTodoResponseDto(
        val id: Long,
        val title: String,
        val content: String
) {
    constructor(todos: Todos) : this(todos.id, todos.title, todos.content)
}