package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ActivityDto {
    private UUID id;
    private Long userId;
    private List<PositionDataDto> positionData;
    private List<HeartbeatDataDto> heartbeatData;
    private List<MovementDataDto> movementData;
    private List<StepDataDto> stepData;
}