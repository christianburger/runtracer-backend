package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.time.LocalDateTime; // Imported LocalDateTime
import java.util.UUID;

@Data
public class StepDataDto {
    private UUID stepDataId; // Changed from activityId to stepDataId
    private UUID activityId;
    private LocalDateTime timestamp; // Changed from Long to LocalDateTime
    private Integer steps;
}