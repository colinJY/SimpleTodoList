package com.colin.example.simpletodolist.todo.dto

import com.colin.example.simpletodolist.todo.domain.Todos

data class SelectTodoListResponseDto(
        val id: Long,
        val title: String,
        val content: String
) {
    constructor(todo: Todos) : this(
            id = todo.id,
            title = todo.title,
            content = todo.content
    )
}