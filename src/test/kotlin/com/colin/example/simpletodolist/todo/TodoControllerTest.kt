package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.global.exception.ErrorCode
import com.colin.example.simpletodolist.todo.domain.Todos
import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
internal class TodoControllerTest(
        @Autowired val webMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper,
        @Autowired val todosRepository: TodosRepository
) {
    @BeforeEach
    fun setUp() {
        todosRepository.deleteAll()
    }

    @Test
    fun `todo_등록_성공`() {
        // given
        val title = "테스트제목"
        val content = "테스트내용"
        val request = InsertTodoRequestDto(title, content)

        // when & then
        val andReturn = webMvc.post("/todos") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            this.content = objectMapper.writeValueAsString(request)
        }.andDo {
            print()
        }.andExpect {
            status { isOk }
            jsonPath("$.id") { isNumber }
        }.andReturn()

        val response = objectMapper.readValue(
                andReturn.response.contentAsString,
                InsertTodoResponseDto::class.java
        )

        val byId = todosRepository.findById(response.id).orElse(null)
        assertThat(byId).isNotNull
        assertThat(byId.title).isEqualTo(title)
        assertThat(byId.content).isEqualTo(content)
    }

    @Test
    fun `todo_조회_확인`() {
        // given
        val savedTodo = createTodoItem()

        // when & then
        webMvc.get("/todos/${savedTodo.id}") {
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isOk }
            jsonPath("$.id") { value(savedTodo.id) }
            jsonPath("$.title") { value(savedTodo.title) }
            jsonPath("$.content") { value(savedTodo.content) }
        }
    }

    private fun createTodoItem(): Todos {
        val title = "테스트제목"
        val content = "테스트내용"

        val todo = Todos(title, content)
        val savedTodo = todosRepository.save(todo)
        return savedTodo
    }

    @Test
    fun `todo_조회_실패`() {

        webMvc.get("/todos/999") {
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isNotFound }
            jsonPath("$.code") { isNotEmpty }
            jsonPath("$.message") { isNotEmpty }
            jsonPath("$.detailMessage") { isNotEmpty }
            jsonPath("$.errors") { doesNotExist() }
        }
    }
    
    @Test
    fun `todo_조회_입력값_타입불일치`() {
        // given
        val id = "invalid_input"
        val errorCode = ErrorCode.INVALID_TYPE_VALUE

        // when & then
        webMvc.get("/todos/$id") {
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isBadRequest }
            jsonPath("$.code") { value(errorCode.code) }
            jsonPath("$.message") { value(errorCode.message) }
        }
    }

    @Test
    fun `todo_리스트조회_성공`() {
        // givin
        val savedTodo = createTodoItem()

        // when & then
        webMvc.get("/todos") {
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isOk }
            jsonPath("$.content.length()") { value(1) }
            jsonPath("$.content.[0].id") { value(savedTodo.id) }
            jsonPath("$.content.[0].title") { value(savedTodo.title) }
            jsonPath("$.content.[0].content") { value(savedTodo.content) }
        }
    }

    @Test
    fun `todo_리스트조회_공집합`() {

        webMvc.get("/todos") {
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isOk }
            jsonPath("$.content.length()") { value(0) }
        }
    }
}