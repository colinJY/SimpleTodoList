package com.colin.example.simpletodolist.common.exception

open class BusinessException(
        val errorCode: ErrorCode,
        val detailMessage: String
) : RuntimeException(errorCode.message)