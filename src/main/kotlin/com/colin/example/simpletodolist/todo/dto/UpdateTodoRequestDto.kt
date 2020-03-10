package com.colin.example.simpletodolist.todo.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UpdateTodoRequestDto(
        @get:NotBlank(message = "제목을 입력하여 주십시오.")
        @get:Size(max = 200, message = "최대 200byte까지 입력 가능합니다.")
        val content: String = ""
)
