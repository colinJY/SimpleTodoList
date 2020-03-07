package com.colin.example.simpletodolist.common.dto

import org.springframework.http.HttpStatus

data class ErrorResponse(
        val httpStatus: HttpStatus,
        val message: String,
        val detailMessage: String,
        val errors: List<FieldError>?
) {
    data class FieldError(
            val field: String,
            val value: String,
            val reason: String
    )
}