package com.colin.example.simpletodolist.todo.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TodosRepository : JpaRepository<Todos, Long> {
}