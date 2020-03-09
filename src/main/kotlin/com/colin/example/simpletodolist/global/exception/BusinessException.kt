package com.colin.example.simpletodolist.global.exception

abstract class BusinessException(
        val errorCode: ErrorCode,
        val detailMessage: String
) : RuntimeException(errorCode.message)