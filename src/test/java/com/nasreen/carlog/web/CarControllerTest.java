package com.nasreen.carlog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.request.CarCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldCreateCarWithRightParams() throws Exception {
        CarCreateRequest createRequest = new CarCreateRequest("Toyota", "RAV4", 2018, "LE");
        this.mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(objectMapper.writeValueAsString(createRequest)));
    }

    @Test
    public void canNotCreateCarWhenTrimIsMissing() throws Exception {
        this.mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'make':'Toyota','model':'RAV4','year':1978}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canNotCreateCarWhenYearIsMissing() throws Exception {
        this.mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'make':'Toyota','model':'RAV4','trim':'LE'}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canNotCreateCarWhenModelIsMissing() throws Exception {
        this.mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'make':'Toyota','year':1978,'trim':'LE'}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canNotCreateCarWhenMakeIsMissing() throws Exception {
        this.mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'model':'RAV4','year':1978,'trim':'LE'}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}