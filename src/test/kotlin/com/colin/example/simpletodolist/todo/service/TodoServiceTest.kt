package com.colin.example.simpletodolist.todo.service

import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.exception.TodosNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class TodoServiceTest(@Autowired val todosRepository: TodosRepository) {

    @Test
    fun `todo_조회_NOTFOUND_예외확인`() {

        val todoService = TodoService(todosRepository)

        assertThrows<TodosNotFoundException> {
            todoService.selectTodo(999)
        }
    }
}