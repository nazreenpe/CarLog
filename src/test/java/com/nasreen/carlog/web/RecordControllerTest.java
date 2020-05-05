package com.nasreen.carlog.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.Record;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.RecordCreateRequest;
import com.nasreen.carlog.service.CarService;
import com.nasreen.carlog.service.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private RecordService service;

    @Test
    public void shouldCreateMaintenanceRecordWithRightParams() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2018, "LE"));
        RecordCreateRequest request = new RecordCreateRequest(LocalDate.now());
        this.mockMvc.perform(post(String.format("/cars/%s/mrs", car.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    Record record = objectMapper.readValue(result.getResponse().getContentAsString(),
                            Record.class);
                    assertThat(record.getId()).isNotNull();
                    assertThat(record.getDate()).isEqualTo(request.getDate());
                    assertThat(record.getCarId()).isEqualTo(car.getId());
                });
    }

    @Test
    public void shouldNotCreateMaintenanceRecordForNonExistingCars() throws Exception {
        this.mockMvc.perform(post(String.format("/cars/%s/mrs", UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("date", LocalDate.now()))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFetchAllMaintenanceRecordsOfExistingCar() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2018, "LE"));
        Record record1 = service.create(new RecordCreateRequest(LocalDate.now()), car);
        Record record2 = service.create(new RecordCreateRequest(LocalDate.now()), car);
        this.mockMvc.perform(get(String.format("/cars/%s/mrs", car.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<Record> records = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertThat(records)
                            .usingFieldByFieldElementComparator()
                            .containsExactly(record1, record2);
                });
    }

    @Test
    public void shouldFetchExistingMaintenanceRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2018, "LE"));
        Record record1 = service.create(new RecordCreateRequest(LocalDate.now()), car);
        this.mockMvc.perform(get(String.format("/cars/%s/mrs/%s", car.getId(), record1.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Record record = objectMapper.readValue(result.getResponse().getContentAsString(), Record.class);
                    assertThat(record)
                            .usingRecursiveComparison()
                            .isEqualTo(record1);
                });
    }

    @Test
    public void shouldNotFetchNonExistingMaintenanceRecord() throws Exception {
        this.mockMvc.perform(get(String.format("/cars/%s/mrs/%s", UUID.randomUUID(), UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateExistingMaintenanceRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2018, "LE"));
        Record record1 = service.create(new RecordCreateRequest(LocalDate.now()), car);
        LocalDate newDate = LocalDate.now().plusDays(1);
        this.mockMvc.perform(put(String.format("/cars/%s/mrs/%s", car.getId(), record1.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(Map.of("date", newDate))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Record record = objectMapper.readValue(result.getResponse().getContentAsString(), Record.class);
                    assertThat(record.getDate()).isEqualTo(newDate);
                });
    }

    @Test
    public void shouldDeleteExistingMaintenanceRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2018, "LE"));
        Record record1 = service.create(new RecordCreateRequest(LocalDate.now()), car);
        this.mockMvc.perform(delete(String.format("/cars/%s/mrs/%s", car.getId(), record1.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
        assertThat(service.get(car, record1.getId())).isEmpty();
    }

    @Test
    public void shouldNotDeleteNonExistingMaintenanceRecord() throws Exception {
        this.mockMvc.perform(delete(String.format("/cars/%s/mrs/%s", UUID.randomUUID(), UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
