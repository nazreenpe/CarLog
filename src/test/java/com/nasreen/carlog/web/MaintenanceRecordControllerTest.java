package com.nasreen.carlog.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.MaintenanceRecord;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.MaintenanceRecordCreateRequest;
import com.nasreen.carlog.service.CarService;
import com.nasreen.carlog.service.MaintenanceRecordService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MaintenanceRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private MaintenanceRecordService service;

    @Test
    public void shouldCreateMaintenanceRecordWithRightParams() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2018, "LE"));
        MaintenanceRecordCreateRequest request = new MaintenanceRecordCreateRequest(LocalDate.now());
        this.mockMvc.perform(post(String.format("/cars/%s/mrs", car.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    MaintenanceRecord record = objectMapper.readValue(result.getResponse().getContentAsString(),
                            MaintenanceRecord.class);
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
        MaintenanceRecord record1 = service.create(new MaintenanceRecordCreateRequest(LocalDate.now()), car);
        MaintenanceRecord record2 = service.create(new MaintenanceRecordCreateRequest(LocalDate.now()), car);
        this.mockMvc.perform(get(String.format("/cars/%s/mrs", car.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<MaintenanceRecord> records = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });
                    assertThat(records)
                            .usingFieldByFieldElementComparator()
                            .containsExactly(record1, record2);
                });
    }

}
