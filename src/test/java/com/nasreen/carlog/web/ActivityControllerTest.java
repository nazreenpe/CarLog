package com.nasreen.carlog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.model.ActivityType;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.MaintenanceRecord;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.MaintenanceRecordCreateRequest;
import com.nasreen.carlog.service.ActivityService;
import com.nasreen.carlog.service.CarService;
import com.nasreen.carlog.service.MaintenanceRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityService service;

    @Autowired
    private CarService carService;

    @Autowired
    private MaintenanceRecordService recordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateAnActivityForExistingRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2020, "XLE"));
        MaintenanceRecord record = recordService.create(new MaintenanceRecordCreateRequest(LocalDate.now()), car);
        this.mockMvc.perform(post(String.format("/cars/%s/mrs/%s/as", car.getId(), record.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(Map.of("type", "TIRE_ROTATION"))))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(result -> {
            Activity activity = objectMapper.readValue(result.getResponse().getContentAsByteArray(), Activity.class);
            assertThat(activity.getId()).isNotNull();
            assertThat(activity.getType()).isEqualTo(ActivityType.TIRE_ROTATION);
        });
    }

    @Test
    public void shouldNotCreateAnActivityForNonExistingRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2020, "XLE"));
        this.mockMvc.perform(post(String.format("/cars/%s/mrs/%s/as", car.getId(), UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(Map.of("type", "TIRE_ROTATION"))))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}