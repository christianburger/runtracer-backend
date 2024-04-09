package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.dto.ActivityDto;
import com.runtracer.runtracerbackend.mappers.*;
import com.runtracer.runtracerbackend.model.activity.Activity;
import com.runtracer.runtracerbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private HeartbeatDataMapper heartbeatDataMapper;
    @Autowired
    private StepDataMapper stepDataMapper;
    @Autowired
    private PositionDataMapper positionDataMapper;
    @Autowired
    private MovementDataMapper movementDataMapper;
    @Autowired
    private HeartbeatDataRepository heartbeatDataRepository;
    @Autowired
    private StepDataRepository stepDataRepository;
    @Autowired
    private PositionDataRepository positionDataRepository;
    @Autowired
    private MovementDataRepository movementDataRepository;

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
    public Mono<Activity> update(UUID id, Activity entity) {
        return Mono.empty();
    }

    @Override
    public Mono<ActivityDto> saveDto(ActivityDto activityDto) {

        Activity activity = activityMapper.toEntity(activityDto);

        // Save the Activity using the ActivityRepository
        return activityRepository.save(activity).flatMap(savedActivity -> {
            // Set the saved activity's ID to the other entities
            UUID activityId = savedActivity.getActivityId();

            // Save the other entities
            return Mono.when(Flux.fromIterable(activityDto.getHeartbeatData()).map(heartbeatDataMapper::toEntity).doOnNext(data -> data.setActivityId(activityId)).flatMap(heartbeatDataRepository::save),
                    Flux.fromIterable(activityDto.getStepData()).map(stepDataMapper::toEntity).doOnNext(data -> data.setActivityId(activityId)).flatMap(stepDataRepository::save),
                    Flux.fromIterable(activityDto.getPositionData()).map(positionDataMapper::toEntity).doOnNext(data -> data.setActivityId(activityId)).flatMap(positionDataRepository::save),
                    Flux.fromIterable(activityDto.getMovementData()).map(movementDataMapper::toEntity).doOnNext(data -> data.setActivityId(activityId)).flatMap(movementDataRepository::save)).thenReturn(savedActivity);
        }).map(activityMapper::toDto);  // Convert the saved Activity back to ActivityDto
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return activityRepository.deleteById(id);
    }
}