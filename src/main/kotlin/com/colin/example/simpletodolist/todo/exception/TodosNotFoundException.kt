package com.colin.example.simpletodolist.todo.exception

import com.colin.example.simpletodolist.common.exception.EntityNotFoundException

class TodosNotFoundException(detailMessage: String) : EntityNotFoundException(detailMessage)