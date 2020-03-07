package com.colin.example.simpletodolist.todo.dto

import javax.validation.constraints.NotBlank

data class SelectTodoRequestDto (
        @get:NotBlank(message = "ID를 입력하여 주십시오.")
        val id: Long
)
