package com.colin.example.simpletodolist.common

import com.colin.example.simpletodolist.common.dto.ErrorResponse
import com.colin.example.simpletodolist.common.exception.BusinessException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(businessException: BusinessException): ResponseEntity<ErrorResponse> {
        logger.error(businessException.toString())

        val errorCode = businessException.errorCode
        val detailMessage = businessException.detailMessage

        val errorResponse = ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                detailMessage = detailMessage
        )

        return ResponseEntity<ErrorResponse>(errorResponse, errorCode.httpStatus)
    }
}