package com.nasreen.carlog.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.model.ActivityType;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.Record;
import com.nasreen.carlog.request.ActivityCreate;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.RecordCreateRequest;
import com.nasreen.carlog.service.ActivityService;
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
class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityService service;

    @Autowired
    private CarService carService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateAnActivityForExistingRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
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

    @Test
    public void shouldListAllActivitiesForExistingRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
        Activity activity1 = service.create(record.getId(), new ActivityCreate(ActivityType.REPLACE_WIPER));
        Activity activity2 = service.create(record.getId(), new ActivityCreate(ActivityType.TIRE_ROTATION));
        Activity activity3 = service.create(record.getId(), new ActivityCreate(ActivityType.OIL_CHANGE));
        this.mockMvc.perform(get(String.format("/cars/%s/mrs/%s/as", car.getId(), record.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<Activity> activities = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                            new TypeReference<>() {
                            });
                    assertThat(activities).usingFieldByFieldElementComparator()
                            .containsExactlyInAnyOrder(activity1, activity2, activity3);
                });
    }

    @Test
    public void shouldNotListActivitiesForNonExistingRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2020, "XLE"));
        this.mockMvc.perform(get(String.format("/cars/%s/mrs/%s/as", car.getId(), UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFetchAnActivityForExistingRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
        service.create(record.getId(), new ActivityCreate(ActivityType.REPLACE_WIPER));
        Activity activity2 = service.create(record.getId(), new ActivityCreate(ActivityType.TIRE_ROTATION));
        this.mockMvc.perform(get(String.format("/cars/%s/mrs/%s/as/%s", car.getId(), record.getId(), activity2.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Activity activity = objectMapper.readValue(result.getResponse().getContentAsByteArray(), Activity.class);
                    assertThat(activity).usingRecursiveComparison()
                            .isEqualTo(activity2);
                });
    }

    @Test
    public void shouldNotFetchNonExistingActivity() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "RAV4", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
        service.create(record.getId(), new ActivityCreate(ActivityType.REPLACE_WIPER));
        Activity activity2 = service.create(record.getId(), new ActivityCreate(ActivityType.TIRE_ROTATION));
        this.mockMvc.perform(get(String.format("/cars/%s/mrs/%s/as/%s", car.getId(), record.getId(), UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateAnActivityForExistingRecord() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "Prius", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
        Activity activity = service.create(record.getId(), new ActivityCreate(ActivityType.TIRE_ROTATION));
        ActivityType updatedType = ActivityType.OIL_CHANGE;
        this.mockMvc.perform(put(String.format("/cars/%s/mrs/%s/as/%s", car.getId(), record.getId(), activity.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(Map.of("type", updatedType))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Activity updatedActivity = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                            Activity.class);
                    assertThat(updatedActivity.getType()).isEqualTo(updatedType);
                });
    }

    @Test
    public void shouldNotUpdateAnNonExistingActivity() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "Prius", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
        ActivityType updatedType = ActivityType.OIL_CHANGE;
        this.mockMvc.perform(put(String.format("/cars/%s/mrs/%s/as/%s", car.getId(), record.getId(), UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(Map.of("type", updatedType))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteAnExistingActivity() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "Prius", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
        Activity activity = service.create(record.getId(), new ActivityCreate(ActivityType.TIRE_ROTATION));
        this.mockMvc.perform(delete(String.format("/cars/%s/mrs/%s/as/%s", car.getId(), record.getId(), activity.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void shouldNotDeleteNonExistingActivity() throws Exception {
        Car car = carService.create(new CarCreateRequest("Toyota", "Prius", 2020, "XLE"));
        Record record = recordService.create(new RecordCreateRequest(LocalDate.now()), car);
        this.mockMvc.perform(delete(String.format("/cars/%s/mrs/%s/as/%s", car.getId(), record.getId(),
                UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}