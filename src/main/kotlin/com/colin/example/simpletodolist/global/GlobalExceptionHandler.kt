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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class GlobalExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
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

    /**
     * binding 시 타입불일치로 발생한다.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handlerMethodArgumentTypeMismatchException(methodArgumentTypeMismatchException: MethodArgumentTypeMismatchException)
            : ResponseEntity<ErrorResponse>
    {
        logger.error(methodArgumentTypeMismatchException.toString())

        val errorCode = ErrorCode.INVALID_TYPE_VALUE

        val fieldError = ErrorResponse.FieldError(
                field = methodArgumentTypeMismatchException.name,
                value = methodArgumentTypeMismatchException.value?.toString() ?: "",
                reason = methodArgumentTypeMismatchException.message ?: ""
        )

        val errorResponse = ErrorResponse(
                code = errorCode.code,
                message = errorCode.message,
                detailMessage = "",
                errors = listOf(fieldError)
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