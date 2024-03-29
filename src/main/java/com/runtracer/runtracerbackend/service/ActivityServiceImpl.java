package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.activity.Activity;
import com.runtracer.runtracerbackend.repository.ActivityRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Mono<Activity> findById(UUID id) {
        return activityRepository.findById(id);
    }

    @Override
    public Flux<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public <S extends Activity> Mono<S> save(S entity) {
        return activityRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return activityRepository.deleteById(id).then(Mono.empty());
    }

    @Override
    public Mono<Activity> update(UUID id, Activity entity) {
        return activityRepository.findById(id)
                .flatMap(existingActivity -> {
                    entity.setId(existingActivity.getId());
                    return activityRepository.save(entity);
                });
    }
}