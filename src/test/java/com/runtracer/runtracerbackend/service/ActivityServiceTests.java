package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.activity.Activity;
import com.runtracer.runtracerbackend.repository.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTests {

    @InjectMocks
    private ActivityServiceImpl activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    public void findByIdTest() {
        UUID id = UUID.randomUUID();
        Activity activity = new Activity();
        activity.setId(id);

        when(activityRepository.findById(id)).thenReturn(Mono.just(activity));

        StepVerifier.create(activityService.findById(id))
                .expectNext(activity)
                .verifyComplete();
    }

    @Test
    public void findAllTest() {
        Activity activity1 = new Activity();
        activity1.setId(UUID.randomUUID());
        Activity activity2 = new Activity();
        activity2.setId(UUID.randomUUID());

        when(activityRepository.findAll()).thenReturn(Flux.just(activity1, activity2));

        StepVerifier.create(activityService.findAll())
                .expectNext(activity1, activity2)
                .verifyComplete();
    }

    @Test
    public void saveTest() {
        Activity activity = new Activity();
        activity.setId(UUID.randomUUID());

        when(activityRepository.save(any(Activity.class))).thenReturn(Mono.just(activity));

        StepVerifier.create(activityService.save(activity))
                .expectNext(activity)
                .verifyComplete();
    }

    @Test
    public void deleteByIdTest() {
        UUID id = UUID.randomUUID();

        when(activityRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(activityService.deleteById(id))
                .verifyComplete();
    }

    @Test
    public void updateTest() {
        UUID id = UUID.randomUUID();
        Activity activity = new Activity();
        activity.setId(id);

        when(activityRepository.findById(id)).thenReturn(Mono.just(activity));
        when(activityRepository.save(any(Activity.class))).thenReturn(Mono.just(activity));

        StepVerifier.create(activityService.update(id, activity))
                .expectNext(activity)
                .verifyComplete();
    }
}