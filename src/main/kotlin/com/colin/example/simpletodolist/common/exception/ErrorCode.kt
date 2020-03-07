package com.colin.example.simpletodolist.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val statusCode: Int,
        val code: String,
        val message: String
) {
    ENTITY_NOT_FOUND(400, "B001", "Entity Not Found")
}
