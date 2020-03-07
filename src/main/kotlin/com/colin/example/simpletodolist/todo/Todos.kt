package com.colin.example.simpletodolist.todo

import javax.persistence.*

@Entity
class Todos(id: Long, title: String, content: String) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = id
        private set

    @Column(nullable = false)
    var title: String = title
        private set

    @Column(nullable = false)
    var content: String = content
        private set

}