package com.nasreen.carlog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private CarService carService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldCreateCarWithRightParams() throws Exception {
        CarCreateRequest createRequest = new CarCreateRequest("Toyota", "RAV4", 2018, "LE");
        this.mockMvc.perform(post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    Car createdCar = objectMapper.readValue(result.getResponse().getContentAsString(), Car.class);
                    assertThat(createdCar.getId()).isNotNull();
                    assertThat(createdCar.getMake()).isEqualTo("Toyota");
                    assertThat(createdCar.getModel()).isEqualTo("RAV4");
                    assertThat(createdCar.getTrim()).isEqualTo("LE");
                    assertThat(createdCar.getYear()).isEqualTo(2018);
                });
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

    @Test
    public void shouldFetchCarWithId() throws Exception {
        CarCreateRequest createRequest = new CarCreateRequest("Toyota", "RAV4", 2018, "LE");
        Car createdCar = carService.create(createRequest);
        this.mockMvc.perform(get(String.format("/cars/%s", createdCar.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    Car fetchedCar = objectMapper.readValue(result.getResponse().getContentAsString(), Car.class);
                    assertThat(fetchedCar.getId()).isEqualTo(createdCar.getId());
                    assertThat(fetchedCar.getMake()).isEqualTo("Toyota");
                    assertThat(fetchedCar.getModel()).isEqualTo("RAV4");
                    assertThat(fetchedCar.getTrim()).isEqualTo("LE");
                    assertThat(fetchedCar.getYear()).isEqualTo(2018);
                });
    }

    @Test
    public void shouldNotFetchNonExistingCar() throws Exception {
        CarCreateRequest createRequest = new CarCreateRequest("Toyota", "RAV4", 2018, "LE");
        this.mockMvc.perform(get(String.format("/cars/%s", UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}