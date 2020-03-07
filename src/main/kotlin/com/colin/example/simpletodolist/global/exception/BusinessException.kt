package com.colin.example.simpletodolist.global.exception

open class BusinessException(
        val errorCode: ErrorCode,
        val detailMessage: String
) : RuntimeException(errorCode.message)