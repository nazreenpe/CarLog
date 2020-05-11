package com.nasreen.carlog.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.WithMockAuthScope;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarRequest;
import com.nasreen.carlog.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockAuthScope
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
        CarRequest createRequest = new CarRequest("Toyota", "RAV4", 2018, "LE");
        this.mockMvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON)
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
        this.mockMvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'make':'Toyota','model':'RAV4','year':1978}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canNotCreateCarWhenYearIsMissing() throws Exception {
        this.mockMvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'make':'Toyota','model':'RAV4','trim':'LE'}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canNotCreateCarWhenModelIsMissing() throws Exception {
        this.mockMvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'make':'Toyota','year':1978,'trim':'LE'}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void canNotCreateCarWhenMakeIsMissing() throws Exception {
        this.mockMvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON)
                .content("{'model':'RAV4','year':1978,'trim':'LE'}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFetchCarWithId() throws Exception {
        CarRequest createRequest = new CarRequest("Toyota", "RAV4", 2018, "LE");
        Car createdCar = carService.create(createRequest);
        this.mockMvc.perform(get(String.format("/api/cars/%s", createdCar.getId()))
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
        this.mockMvc.perform(get(String.format("/api/cars/%s", UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldBeAbleToDeleteAnExistingCar() throws Exception {
        CarRequest createRequest = new CarRequest("Toyota", "RAV4", 2018, "LE");
        Car createdCar = carService.create(createRequest);
        this.mockMvc.perform(delete(String.format("/api/cars/%s", createdCar.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotDeleteNonExistingCar() throws Exception {
        this.mockMvc.perform(delete(String.format("/api/cars/%s", UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateExistingCar() throws Exception {
        CarRequest createRequest = new CarRequest("Toyota", "RAV4", 2018, "LE");
        Car createdCar = carService.create(createRequest);
        this.mockMvc.perform(put(String.format("/api/cars/%s", createdCar.getId()))
                .content(objectMapper.writeValueAsString(Map.of(
                        "make", "Toyota",
                        "trim", "SE Sport",
                        "year", 2020,
                        "model", "RAV4"
                )))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(result -> {
                    Car fetchedCar = objectMapper.readValue(result.getResponse().getContentAsString(), Car.class);
                    assertThat(fetchedCar.getId()).isEqualTo(createdCar.getId());
                    assertThat(fetchedCar.getMake()).isEqualTo("Toyota");
                    assertThat(fetchedCar.getModel()).isEqualTo("RAV4");
                    assertThat(fetchedCar.getTrim()).isEqualTo("SE Sport");
                    assertThat(fetchedCar.getYear()).isEqualTo(2020);
                });
    }

    @Test
    public void shouldNotUpdateNonExistingCar() throws Exception {
        this.mockMvc.perform(put(String.format("/api/cars/%s", UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("trim", "SE Sport"))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFetchAllExistingCars() throws Exception {
        carService.deleteAll();
        CarRequest createRequest1 = new CarRequest("Toyota", "RAV4", 2018, "LE");
        Car createdCar1 = carService.create(createRequest1);
        CarRequest createRequest2 = new CarRequest("Toyota", "PRIUS", 2018, "LE");
        Car createdCar2 = carService.create(createRequest2);
        this.mockMvc.perform(get("/api/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<Car> fetchedCars = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertThat(fetchedCars)
                            .usingFieldByFieldElementComparator()
                            .containsExactly(createdCar1, createdCar2);
                });
    }

    @Test
    public void responseShouldHaveCookie() throws Exception {
        this.mockMvc.perform(get("/api/cars")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(cookie().exists("SESSION"));
    }
}
