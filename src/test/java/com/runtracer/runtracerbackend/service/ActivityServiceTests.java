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
        Long id = 1L;
        Activity activity = new Activity();
        activity.setActivityId(id);

        when(activityRepository.findById(id)).thenReturn(Mono.just(activity));

        StepVerifier.create(activityService.findById(id))
                .expectNext(activity)
                .verifyComplete();
    }

    @Test
    public void findAllTest() {
        Activity activity1 = new Activity();
        activity1.setActivityId(1L);
        Activity activity2 = new Activity();
        activity2.setActivityId(2L);

        when(activityRepository.findAll()).thenReturn(Flux.just(activity1, activity2));

        StepVerifier.create(activityService.findAll())
                .expectNext(activity1, activity2)
                .verifyComplete();
    }

    @Test
    public void saveTest() {
        Activity activity = new Activity();
        activity.setActivityId(1L);

        when(activityRepository.save(any(Activity.class))).thenReturn(Mono.just(activity));

        StepVerifier.create(activityService.save(activity))
                .expectNext(activity)
                .verifyComplete();
    }

    @Test
    public void deleteByIdTest() {
        Long id = 1L;

        when(activityRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(activityService.deleteById(id))
                .verifyComplete();
    }

    @Test
    public void updateTest() {
        Long id = 1L;
        Activity activity = new Activity();
        activity.setActivityId(id);

        when(activityRepository.save(any(Activity.class))).thenReturn(Mono.empty());

        StepVerifier.create(activityService.update(id, activity))
                .verifyComplete();
    }
}