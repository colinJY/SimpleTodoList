package com.colin.example.simpletodolist.common

import com.colin.example.simpletodolist.common.dto.ErrorResponse
import com.colin.example.simpletodolist.common.exception.BusinessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(businessException: BusinessException): ResponseEntity<ErrorResponse> {

        val httpStatus = businessException.errorCode.statusCode
        val errorResponse = ErrorResponse(
                message = businessException.errorCode.message,
                detailMessage = businessException.detailMessage
        )

        return ResponseEntity<ErrorResponse>(errorResponse, httpStatus)
    }
}