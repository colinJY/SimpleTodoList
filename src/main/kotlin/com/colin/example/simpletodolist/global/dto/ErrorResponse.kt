package com.colin.example.simpletodolist.global.dto

import org.springframework.validation.BindingResult

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
    ) {
        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> {
                return bindingResult.fieldErrors.map {
                    FieldError(
                            field = it.field,
                            value = it.rejectedValue?.toString() ?: "",
                            reason = it.defaultMessage ?: ""
                    )
                }.toList()
            }
        }
    }
}