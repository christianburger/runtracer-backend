package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.time.LocalDateTime; // Imported LocalDateTime
import java.util.List;
import java.util.UUID;

@Data
public class ActivityDto {
    private UUID activityId;
    private UUID userId;
    private List<PositionDataDto> positionData;
    private List<HeartbeatDataDto> heartbeatData;
    private List<MovementDataDto> movementData;
    private List<StepDataDto> stepData;
    private LocalDateTime startTime; // Added startTime field with LocalDateTime type
    private LocalDateTime endTime; // Added endTime field with LocalDateTime type
}
