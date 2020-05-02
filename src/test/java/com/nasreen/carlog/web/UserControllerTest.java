package com.nasreen.carlog.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.UserCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateUserWithRightParams() throws Exception {
        UserCreateRequest createRequest = new UserCreateRequest(
                "mocking_bird", "bird_123@gmail.com", "password"
        );
        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    User createdUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
                    assertThat(createdUser.getId()).isNotNull();
                    assertThat(createdUser.getIsAdmin()).isEqualTo(false);
                    assertThat(createdUser.getUsername()).isEqualTo("mocking_bird");
                    assertThat(createdUser.getEmailId()).isEqualTo("bird_123@gmail.com");
//                    assertThat(createdUser.getPassword()).isEqualTo(2018);
                });
    }
}