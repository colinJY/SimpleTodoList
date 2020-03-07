package com.colin.example.simpletodolist.global.dto

data class ErrorResponse(
        val code: String,
        val message: String,
        val detailMessage: String,
        val errors: List<FieldError>? = null
) {
    data class FieldError(
            val field: String,
            val value: String,
            val reason: String
    )
}