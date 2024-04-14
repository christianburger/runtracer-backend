package com.runtracer.runtracerbackend.utils;

import com.runtracer.runtracerbackend.dto.*;
import com.runtracer.runtracerbackend.dtos.ApiRoleDto;
import com.runtracer.runtracerbackend.mappers.ApiUserResponseMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Setter
@Getter
@Component
public class TestUtils {
    private static final ApiClient API_CLIENT = new ApiClient();
    private final ApiUserResponseMapper apiUserResponseMapper;

    public TestUtils(ApiUserResponseMapper apiUserResponseMapper) {
        this.apiUserResponseMapper = apiUserResponseMapper;
    }

    public UserDto createUserDto() {
        log.info("createUserDto called");
        UserDto userDto = new UserDto();
        try {
            log.info("Calling API_CLIENT.getUserData()");
            ApiUserResponse apiUserResponse = API_CLIENT.getUserData();
            log.info("API_CLIENT.getUserData() returned: {}", apiUserResponse);
            ApiUserResponse.UserDto apiUserDto = apiUserResponse.getResults().get(0);
            log.info("apiUserDto: {}", apiUserDto);

            // Generate a random UUID and set it as the userId of the apiUserDto
            UUID randomUUID = UUID.randomUUID();
            apiUserDto.setUserId(randomUUID.toString());

            // Generate a random UUID for each role in the roles list
            for (ApiRoleDto role : apiUserDto.getRoles()) {
                UUID randomRoleUUID = UUID.randomUUID();
                role.setRoleId(randomRoleUUID.toString());
            }

            userDto = apiUserResponseMapper.toUserDto(apiUserDto);
            userDto.setUserId(randomUUID);
            log.info("Mapped apiUserDto to userDto: {}", userDto);

        } catch (Exception e) {
            log.error("Error creating UserDto from API data", e);
        }
        log.info("createUserDto returning: {}", userDto);
        return userDto;
    }

    public ActivityDto createActivityDto(UUID id, int numElements) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setActivityId(id);
        activityDto.setUserId(id);
        activityDto.setPositionData(createPositionDataDto(id, numElements));
        activityDto.setHeartbeatData(createHeartbeatDataDto(id, numElements));
        activityDto.setMovementData(createMovementDataDto(id, numElements));
        activityDto.setStepData(createStepDataDto(id, numElements));
        log.info("ActivityDto: {}", activityDto);
        return activityDto;
    }

    public List<PositionDataDto> createPositionDataDto(UUID id, int numElements) {
        List<PositionDataDto> positionDataDtoList = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            PositionDataDto positionDataDto = new PositionDataDto();
            positionDataDto.setActivityId(id);
            positionDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
            positionDataDto.setLatitude((long) (id.getLeastSignificantBits() % 180 - 90)); // latitude range -90 to 90
            positionDataDto.setLongitude((long) (id.getLeastSignificantBits() % 360 - 180)); // longitude range -180 to 180
            positionDataDto.setHeight((int) (id.getLeastSignificantBits() % 100)); // assuming height range 0-100
            log.info("PositionDataDto: {}", positionDataDto);
            positionDataDtoList.add(positionDataDto);
        }
        return positionDataDtoList;
    }

    public List<HeartbeatDataDto> createHeartbeatDataDto(UUID id, int numElements) {
        List<HeartbeatDataDto> heartbeatDataDtoList = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            HeartbeatDataDto heartbeatDataDto = new HeartbeatDataDto();
            heartbeatDataDto.setActivityId(id);
            heartbeatDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
            heartbeatDataDto.setHeartbeat((int) (id.getLeastSignificantBits() % 200)); // assuming heartbeat range 0-200
            log.info("HeartbeatDataDto: {}", heartbeatDataDto);
            heartbeatDataDtoList.add(heartbeatDataDto);
        }
        return heartbeatDataDtoList;
    }

    public List<MovementDataDto> createMovementDataDto(UUID id, int numElements) {
        List<MovementDataDto> movementDataDtoList = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            MovementDataDto movementDataDto = new MovementDataDto();
            movementDataDto.setActivityId(id);
            movementDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
            movementDataDto.setMoveUp((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
            movementDataDto.setMoveDown((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
            movementDataDto.setMoveLeft((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
            movementDataDto.setMoveRight((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
            log.info("MovementDataDto: {}", movementDataDto);
            movementDataDtoList.add(movementDataDto);
        }
        return movementDataDtoList;
    }

    public List<StepDataDto> createStepDataDto(UUID id, int numElements) {
        List<StepDataDto> stepDataDtoList = new ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            StepDataDto stepDataDto = new StepDataDto();
            stepDataDto.setActivityId(id);
            stepDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
            stepDataDto.setSteps((int) (id.getLeastSignificantBits() % 10000)); // assuming steps range 0-10000
            log.info("StepDataDto: {}", stepDataDto);
            stepDataDtoList.add(stepDataDto);
        }
        return stepDataDtoList;
    }

}
























