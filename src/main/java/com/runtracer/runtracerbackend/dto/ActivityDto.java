package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ActivityDto {
    private Long activityId;
    private Long userId;
    private List<PositionDataDto> positionData;
    private List<HeartbeatDataDto> heartbeatData;
    private List<MovementDataDto> movementData;
    private List<StepDataDto> stepData;
}