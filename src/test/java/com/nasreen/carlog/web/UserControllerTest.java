package com.nasreen.carlog.web;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockAuthScope
class UserControllerTest {
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
                "mocking_bird", "bird_123@gmail.com", "password"
        );
        this.mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    User createdUser = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
                    assertThat(createdUser.getId()).isNotNull();
                    assertThat(createdUser.getIsAdmin()).isEqualTo(false);
                    assertThat(createdUser.getName()).isEqualTo("mocking_bird");
                    assertThat(createdUser.getEmailId()).isEqualTo("bird_123@gmail.com");
                });
    }
}