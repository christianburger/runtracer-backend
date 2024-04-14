package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.time.LocalDateTime; // Imported LocalDateTime
import java.util.UUID;

@Data
public class MovementDataDto {
    private UUID movementDataId; // Changed from activityId to movementDataId
    private UUID activityId;
    private LocalDateTime timestamp; // Changed from Long to LocalDateTime
    private Integer moveUp;
    private Integer moveDown;
    private Integer moveLeft;
    private Integer moveRight;
}