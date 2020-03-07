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

        val errorResponse = ErrorResponse(
                httpStatus = businessException.errorCode.statusCode,
                message = businessException.errorCode.message,
                detailMessage = businessException.detailMessage
        )

        return ResponseEntity<ErrorResponse>(errorResponse, errorResponse.httpStatus)
    }
}