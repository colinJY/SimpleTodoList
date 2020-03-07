package com.colin.example.simpletodolist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleTodoListApplication

fun main(args: Array<String>) {
    runApplication<SimpleTodoListApplication>(*args)
}
