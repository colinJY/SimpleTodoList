package com.colin.example.simpletodolist.todo

import com.colin.example.simpletodolist.todo.domain.TodosRepository
import com.colin.example.simpletodolist.todo.dto.InsertTodoRequestDto
import com.colin.example.simpletodolist.todo.dto.InsertTodoResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
internal class TodoControllerTest(
        @Autowired val webMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper,
        @Autowired val todosRepository: TodosRepository
) {

    @Test
    fun `todo_등록_확인`() {
        // given
        val title = "테스트제목"
        val content = "테스트내용"
        val request = InsertTodoRequestDto(title, content)

        // when
        val andReturn = webMvc.post("/todo") {
            contentType = MediaType.APPLICATION_JSON
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

}