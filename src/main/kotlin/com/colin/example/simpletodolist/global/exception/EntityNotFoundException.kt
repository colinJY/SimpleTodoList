package com.colin.example.simpletodolist.global.exception

open class EntityNotFoundException(detailMessage: String)
    : BusinessException(ErrorCode.ENTITY_NOT_FOUND, detailMessage) {
}