package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.global.exception.ErrorCode
import com.colin.example.simpletodolist.todo.domain.Todos
import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.colin.example.simpletodolist.todo.dto.UpdateTodoRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
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
            status { isCreated }
            jsonPath("$.id") { isNumber }
            jsonPath("$.title") { value(title) }
            jsonPath("$.content") { value(content) }
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
    fun `todo_등록_필수값 예외`() {
        // given
        val request = InsertTodoRequestDto("", "")
        val errorCode = ErrorCode.INVALID_INPUT_VALUE

        // when & then
        webMvc.post("/todos") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            this.content = objectMapper.writeValueAsString(request)
        }.andDo {
            print()
        }.andExpect {
            status { isBadRequest }
            jsonPath("$.code") { value(errorCode.code) }
            jsonPath("$.message") { value(errorCode.message) }
        }
    }

    @Test
    fun `todo_조회_확인`() {
        // given
        val savedTodo = createTodoItem("테스트제목", "테스트내용")

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

    private fun createTodoItem(title: String, content: String): Todos {
        val todo = Todos(title, content)
        return todosRepository.save(todo)
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
        val savedTodo = createTodoItem("테스트타이틀", "테스트내용")

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
    
    @Test
    fun `todo_수정_성공`() {
        // given
        val todoItem = createTodoItem("테스트제목", "테스트내용")
        val updateContent = "수정된 내용"
        todoItem.update(updateContent)

        // when & then
        webMvc.put("/todos/${todoItem.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(UpdateTodoRequestDto(updateContent))
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isOk }
            jsonPath("$.id") { value(todoItem.id) }
            jsonPath("$.title") { value(todoItem.title) }
            jsonPath("$.content") { value(todoItem.content) }
        }

        val byId = todosRepository.findById(todoItem.id).orElse(null)
        assertThat(byId).isNotNull
        assertThat(byId.title).isEqualTo(todoItem.title)
        assertThat(byId.content).isEqualTo(todoItem.content)
    }

    @Test
    fun `todo_수정_선조회_실패`() {
        // given
        val errorCode = ErrorCode.ENTITY_NOT_FOUND
        val detailMessage = "수정할 Todo가 존재하지 않습니다."

        // when & then
        webMvc.put("/todos/999") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(UpdateTodoRequestDto("선조회실패"))
            accept = MediaType.APPLICATION_JSON
        }.andDo {
            print()
        }.andExpect {
            status { isNotFound }
            jsonPath("$.code") { value(errorCode.code) }
            jsonPath("$.message") { value(errorCode.message) }
            jsonPath("$.detailMessage") { value(detailMessage) }
        }
    }

    @Test
    fun `todo_삭제_성공`() {
        // given
        val todo = createTodoItem("테스트제목", "테스트내용")

        // when & then
        webMvc.delete("/todos/${todo.id}").andDo {
            print()
        }.andExpect {
            status { isNoContent }
        }

        val byId = todosRepository.findById(todo.id).orElse(null)

        assertThat(byId).isNull()
    }

    @Test
    fun `todo_삭제_실패`() {
        // given
        val errorCode = ErrorCode.ENTITY_NOT_FOUND

        // when & then
        webMvc.delete("/todos/999").andDo {
            print()
        }.andExpect {
            status { isNotFound }
            jsonPath("$.code") { value(errorCode.code) }
            jsonPath("$.message") { value(errorCode.message) }
        }
    }

    @Test
    fun `todo_리스트_수정_성공`() {
        // given
        val todoItemList = IntRange(0, 9).map {
            createTodoItem("테스트제목${it}", "테스트내용${it}")
        }.mapIndexed { index, todos ->
            todos.apply { update("수정된내용${index}") }
        }.toList()

        // when & then
        webMvc.put("/todos") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(todoItemList)
        }.andDo {
            print()
        }.andExpect {
            status { isNoContent }
        }

        assertThat(todosRepository.count()).isEqualTo(todoItemList.size.toLong())

        todoItemList.forEach { inputTodo ->
            val byId = todosRepository.findById(inputTodo.id).orElse(null)

            assertThat(byId).isNotNull
            assertThat(byId.id).isEqualTo(inputTodo.id)
            assertThat(byId.title).isEqualTo(inputTodo.title)
            assertThat(byId.content).isEqualTo(inputTodo.content)
        }
    }

    @Test
    fun `todo_리스트_수정_실패_전체롤백`() {
        // given
        val originTodoItemList = IntRange(0, 9).map {
            createTodoItem("테스트제목$it", "테스트내용$it")
        }.toList()

        val todoItemList = originTodoItemList.mapIndexed { index, todos ->
            Todos(todos.title, "수정된내용$index", todos.id)
        }.toMutableList()
        // 테이블에 없는 내역 추가
        todoItemList.add(Todos("실패할제목", "실패할내용", 11))

        // 발생될 에러
        val errorCode = ErrorCode.ENTITY_NOT_FOUND

        webMvc.put("/todos") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(todoItemList)
        }.andDo {
            print()
        }.andExpect {
            status { isNotFound }
            jsonPath("$.code") { value(errorCode.code) }
            jsonPath("$.message") { value(errorCode.message) }
        }

        originTodoItemList.forEach { originTodos ->
            val byId = todosRepository.findById(originTodos.id).orElse(null)

            assertThat(byId).isNotNull
            assertThat(byId.id).isEqualTo(originTodos.id)
            assertThat(byId.title).isEqualTo(originTodos.title)
            assertThat(byId.content).isEqualTo(originTodos.content)
        }
    }

}