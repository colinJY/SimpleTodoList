package com.colin.example.simpletodolist.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
        val httpStatus: HttpStatus,
        val code: String,
        val message: String
) {
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "Entity Not Found"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "Invalid Value"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C002", "Invalid Type Value")

//    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
//    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
//    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
//    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
//    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
//    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
}
