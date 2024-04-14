package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.dto.ActivityDto;
import com.runtracer.runtracerbackend.mappers.*;
import com.runtracer.runtracerbackend.model.activity.Activity;
import com.runtracer.runtracerbackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
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
        log.info("Starting saveDto with activityDto: {}", activityDto);

        // Map the ActivityDto to Activity entity
        Activity activity = activityMapper.toEntity(activityDto);

        // Log all parameters of the activityDto
        log.info("ActivityDto parameters: activityId={}, userId={}, startTime={}, endTime={}",
                activityDto.getActivityId(), activityDto.getUserId(), activityDto.getStartTime(), activityDto.getEndTime());

        // Set startTime and endTime if available
        if (activityDto.getStartTime() != null) {
            activity.setStartTime(activityDto.getStartTime());
            log.info("Start time: {}", activity.getStartTime());  // Print start time
        }
        if (activityDto.getEndTime() != null) {
            activity.setEndTime(activityDto.getEndTime());
            log.info("End time: {}", activity.getEndTime());  // Print end time
        }

        log.info("Mapped activityDto to activity: {}", activity);

        if (activity.getActivityId() == null) {
            log.info("Activity ID is null, saving activity and related entities");
            // If the activity's ID is null, save the activity and its related entities
            return saveActivityAndRelatedEntities(activity, activityDto)
                    .doOnSuccess(activityDtoResult -> log.info("Successfully saved activityDto: {}", activityDtoResult))
                    .doOnError(error -> log.error("Error occurred while saving activityDto: {}", error.getMessage(), error));
        } else {
            log.info("Activity ID is not null, checking if activity exists in the database");
            // If the activity's ID is not null, check if the activity exists in the database
            return activityRepository.findById(activity.getActivityId())
                    .flatMap(existingActivity -> {
                        log.info("Activity exists, updating it");
                        // If the activity exists, update it
                        return saveActivityAndRelatedEntities(activity, activityDto)
                                .doOnSuccess(activityDtoResult -> log.info("Successfully updated activityDto: {}", activityDtoResult))
                                .doOnError(error -> log.error("Error occurred while updating activityDto: {}", error.getMessage(), error));
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        log.error("Error occurred: Activity with ID {} does not exist", activity.getActivityId());
                        return Mono.error(new RuntimeException("Activity with ID " + activity.getActivityId() + " does not exist"));
                    }));
        }
    }


    private Mono<ActivityDto> saveActivityAndRelatedEntities(Activity activity, ActivityDto activityDto) {
        log.info("Starting saveActivityAndRelatedEntities with activity: {} and activityDto: {}", activity, activityDto);

        return activityRepository.save(activity)
                .doOnSuccess(savedActivity -> log.info("Successfully saved activity: {}", savedActivity))
                .doOnError(error -> log.error("Error occurred while saving activity: {}", error.getMessage(), error))
                .flatMap(savedActivity -> {
                    // Set the saved activity's ID to the other entities
                    UUID activityId = savedActivity.getActivityId();

                    log.info("Saved activity ID: {}", activityId);

                    // Save the other entities
                    return Mono.when(
                            Flux.fromIterable(activityDto.getHeartbeatData())
                                    .map(heartbeatDataMapper::toEntity)
                                    .doOnNext(data -> {
                                        data.setActivityId(activityId);
                                        log.info("Set activity ID to heartbeatData: {}", data);
                                    })
                                    .flatMap(heartbeatData -> heartbeatDataRepository.save(heartbeatData)
                                            .doOnSuccess(savedHeartbeatData -> log.info("Successfully saved heartbeatData: {}", savedHeartbeatData))
                                            .doOnError(error -> log.error("Error occurred while saving heartbeatData: {}", error.getMessage(), error))),
                            Flux.fromIterable(activityDto.getStepData())
                                    .map(stepDataMapper::toEntity)
                                    .doOnNext(data -> {
                                        data.setActivityId(activityId);
                                        log.info("Set activity ID to stepData: {}", data);
                                    })
                                    .flatMap(stepData -> stepDataRepository.save(stepData)
                                            .doOnSuccess(savedStepData -> log.info("Successfully saved stepData: {}", savedStepData))
                                            .doOnError(error -> log.error("Error occurred while saving stepData: {}", error.getMessage(), error))),
                            Flux.fromIterable(activityDto.getPositionData())
                                    .map(positionDataMapper::toEntity)
                                    .doOnNext(data -> {
                                        data.setActivityId(activityId);
                                        log.info("Set activity ID to positionData: {}", data);
                                    })
                                    .flatMap(positionData -> positionDataRepository.save(positionData)
                                            .doOnSuccess(savedPositionData -> log.info("Successfully saved positionData: {}", savedPositionData))
                                            .doOnError(error -> log.error("Error occurred while saving positionData: {}", error.getMessage(), error))),
                            Flux.fromIterable(activityDto.getMovementData())
                                    .map(movementDataMapper::toEntity)
                                    .doOnNext(data -> {
                                        data.setActivityId(activityId);
                                        log.info("Set activity ID to movementData: {}", data);
                                    })
                                    .flatMap(movementData -> movementDataRepository.save(movementData)
                                            .doOnSuccess(savedMovementData -> log.info("Successfully saved movementData: {}", savedMovementData))
                                            .doOnError(error -> log.error("Error occurred while saving movementData: {}", error.getMessage(), error)))
                    ).thenReturn(savedActivity);
                }).map(activityMapper::toDto)  // Convert the saved Activity back to ActivityDto
                .doOnSuccess(activityDtoResult -> log.info("Successfully mapped saved activity to activityDto: {}", activityDtoResult))
                .doOnError(error -> log.error("Error occurred while mapping saved activity to activityDto: {}", error.getMessage(), error));
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return activityRepository.deleteById(id);
    }
}