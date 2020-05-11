package com.nasreen.carlog.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.WithMockAuthScope;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.UserCreateRequest;
import com.nasreen.carlog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService service;

    @BeforeEach
    public void setup(){
        service.deleteAll();
    }

    @Test
    public void shouldCreateUserWithRightParams() throws Exception {
        UserCreateRequest createRequest = new UserCreateRequest(
                "mocking_bird", "bird_123@gmail.com", "password123456"
        );
        this.mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    Optional<User> createdUser = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertThat(createdUser.get().getId()).isNotNull();
                    assertThat(createdUser.get().getName()).isEqualTo("mocking_bird");
                    assertThat(createdUser.get().getEmailId()).isEqualTo("bird_123@gmail.com");
                });
    }

    @Test
    public void shouldLoginWithExistingUser() throws Exception {
        service.create(new UserCreateRequest("test","test@example.com", "Password123"));
        this.mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(Map.of("email", "test@example.com",
                        "password","Password123"))))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}