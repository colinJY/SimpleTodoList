package com.colin.example.simpletodolist.todo.dto

import com.colin.example.simpletodolist.todo.domain.Todos
import javax.validation.constraints.*

data class InsertTodoRequestDto(
        @get:NotBlank(message = "제목을 입력하여 주십시오.")
        @get:Size(max = 200, message = "최대 200byte까지 입력 가능합니다.")
        val title: String,

        @get:NotBlank(message = "내용을 입력하여 주십시오.")
        @get:Size(max = 2000, message = "최대 2000byte까지 입력 가능합니다.")
        val content: String
) {
    fun toEntity(): Todos = Todos(title, content)
}
