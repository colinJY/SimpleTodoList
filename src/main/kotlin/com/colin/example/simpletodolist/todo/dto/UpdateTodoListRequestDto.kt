package com.colin.example.simpletodolist.todo.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UpdateTodoListRequestDto (
        @get:Min(value = 1, message = "ID값을 입력하여 주십시오")
        val id: Long = 0,

        @get:NotBlank(message = "내용을 입력하여 주십시오.")
        @get:Size(max = 2000, message = "최대 200byte까지 입력 가능합니다.")
        val content: String = ""
)
