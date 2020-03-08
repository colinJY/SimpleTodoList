package com.colin.example.simpletodolist.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val httpStatus: HttpStatus,
        val code: String,
        val message: String
) {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "Entity Not Found"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid value")
}
