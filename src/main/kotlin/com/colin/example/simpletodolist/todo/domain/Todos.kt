package com.colin.example.simpletodolist.todo.domain

import com.colin.example.simpletodolist.common.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class Todos(title: String, content: String) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

    @Column(nullable = false)
    var title: String = title
        private set

    @Column(nullable = false)
    var content: String = content
        private set

}