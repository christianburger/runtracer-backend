package com.runtracer.runtracerbackend.utils;

import com.runtracer.runtracerbackend.dto.*;
import com.runtracer.runtracerbackend.model.Role.RoleType;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
public class TestUtils {
    private static final ApiClient API_CLIENT = new ApiClient();

    public static UserDto createUserDto() {
        UserDto userDto = new UserDto();
        try {
            ApiUserResponse apiUserResponse = API_CLIENT.getUserData();
            ApiUserResponse.UserDto apiUserDto = apiUserResponse.getResults().get(0);

            userDto.setUserId(UUID.fromString(apiUserDto.getId().toString()));
            userDto.setUsername(apiUserDto.getUsername());
            userDto.setPassword(apiUserDto.getPassword());
            userDto.setEmail(apiUserDto.getEmail());

            RoleDto roleDto = new RoleDto();
            roleDto.setRoleId(UUID.randomUUID());
            roleDto.setName(apiUserDto.getRoles().contains("admin") ? RoleType.ROLE_ADMIN : RoleType.ROLE_USER);

            userDto.setRoles(Collections.singletonList(roleDto));
            log.info("UserDto: {}", userDto);
        } catch (Exception e) {
            log.error("Error creating UserDto from API data", e);
        }
        return userDto;
    }

    public static ActivityDto createActivityDto(UUID id) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setActivityId(id);
        activityDto.setUserId(id);
        activityDto.setPositionData(List.of(createPositionDataDto(id)));
        activityDto.setHeartbeatData(List.of(createHeartbeatDataDto(id)));
        activityDto.setMovementData(List.of(createMovementDataDto(id)));
        activityDto.setStepData(List.of(createStepDataDto(id)));
        log.info("ActivityDto: {}", activityDto);
        return activityDto;
    }

    public static PositionDataDto createPositionDataDto(UUID id) {
        PositionDataDto positionDataDto = new PositionDataDto();
        positionDataDto.setActivityId(id);
        positionDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
        positionDataDto.setLatitude((long) (id.getLeastSignificantBits() % 180 - 90)); // latitude range -90 to 90
        positionDataDto.setLongitude((long) (id.getLeastSignificantBits() % 360 - 180)); // longitude range -180 to 180
        positionDataDto.setHeight((int) (id.getLeastSignificantBits() % 100)); // assuming height range 0-100
        log.info("PositionDataDto: {}", positionDataDto);
        return positionDataDto;
    }

    public static HeartbeatDataDto createHeartbeatDataDto(UUID id) {
        HeartbeatDataDto heartbeatDataDto = new HeartbeatDataDto();
        heartbeatDataDto.setActivityId(id);
        heartbeatDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
        heartbeatDataDto.setHeartbeat((int) (id.getLeastSignificantBits() % 200)); // assuming heartbeat range 0-200
        log.info("HeartbeatDataDto: {}", heartbeatDataDto);
        return heartbeatDataDto;
    }

    public static MovementDataDto createMovementDataDto(UUID id) {
        MovementDataDto movementDataDto = new MovementDataDto();
        movementDataDto.setActivityId(id);
        movementDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
        movementDataDto.setMoveUp((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
        movementDataDto.setMoveDown((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
        movementDataDto.setMoveLeft((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
        movementDataDto.setMoveRight((int) (id.getLeastSignificantBits() % 100)); // assuming range 0-100
        log.info("MovementDataDto: {}", movementDataDto);
        return movementDataDto;
    }

    public static StepDataDto createStepDataDto(UUID id) {
        StepDataDto stepDataDto = new StepDataDto();
        stepDataDto.setActivityId(id);
        stepDataDto.setTimestamp(id.getMostSignificantBits() & Long.MAX_VALUE);
        stepDataDto.setSteps((int) (id.getLeastSignificantBits() % 10000)); // assuming steps range 0-10000
        log.info("StepDataDto: {}", stepDataDto);
        return stepDataDto;
    }
}