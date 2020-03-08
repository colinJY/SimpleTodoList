package com.colin.example.simpletodolist.global

import com.colin.example.simpletodolist.global.dto.ErrorResponse
import com.colin.example.simpletodolist.global.exception.BusinessException
import com.colin.example.simpletodolist.global.exception.ErrorCode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerMethodArgumentNotValidException(methodArgumentNotValidException: MethodArgumentNotValidException)
            : ResponseEntity<ErrorResponse>
    {
        logger.error(methodArgumentNotValidException.toString())

        val errorCode = ErrorCode.INVALID_INPUT_VALUE

        val errorResponse = ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                detailMessage = "",
                errors = ErrorResponse.FieldError.of(methodArgumentNotValidException.bindingResult)
        )

        return ResponseEntity<ErrorResponse>(errorResponse, errorCode.httpStatus)
    }


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