package com.colin.example.simpletodolist.global.exception

abstract class EntityNotFoundException(detailMessage: String)
    : BusinessException(ErrorCode.ENTITY_NOT_FOUND, detailMessage) {
}