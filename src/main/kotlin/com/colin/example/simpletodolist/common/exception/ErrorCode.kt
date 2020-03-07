package com.colin.example.simpletodolist.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val httpStatus: HttpStatus,
        val code: String,
        val message: String
) {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "Entity Not Found")
}
