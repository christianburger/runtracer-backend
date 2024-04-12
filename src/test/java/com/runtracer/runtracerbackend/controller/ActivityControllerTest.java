package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.dto.ActivityDto;
import com.runtracer.runtracerbackend.mappers.ActivityMapper;
import com.runtracer.runtracerbackend.model.activity.Activity;
import com.runtracer.runtracerbackend.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Slf4j
public class ActivityControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ActivityService activityService;

    @MockBean
    private ActivityMapper activityMapper;

    @Test
    @WithMockUser
    public void testGetActivity() {
        UUID activityId = UUID.randomUUID();
        UUID userId = UUID.randomUUID(); // replace with actual user ID
        Activity activity = new Activity(activityId, userId);

        ActivityDto expectedActivityDto = new ActivityDto();
        expectedActivityDto.setActivityId(activityId);

        Mockito.when(activityService.findById(activityId)).thenReturn(Mono.just(activity));
        Mockito.when(activityMapper.toDto(activity)).thenReturn(expectedActivityDto);

        webTestClient.get()
                .uri("/api/activities/{id}", activityId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ActivityDto.class)
                .isEqualTo(expectedActivityDto);
    }
}