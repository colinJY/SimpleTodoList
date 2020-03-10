package com.colin.example.simpletodolist.todo.domain

import com.colin.example.simpletodolist.global.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class Todos(title: String, content: String, id: Long = 0) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = id
        private set

    @Column(nullable = false)
    var title: String = title
        private set

    @Column(nullable = false)
    var content: String = content
        private set

    fun update(content: String) {
        this.content = content
    }
}