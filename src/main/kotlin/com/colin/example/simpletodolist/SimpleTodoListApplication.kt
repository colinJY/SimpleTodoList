package com.colin.example.simpletodolist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class SimpleTodoListApplication

fun main(args: Array<String>) {
    runApplication<SimpleTodoListApplication>(*args)
}
