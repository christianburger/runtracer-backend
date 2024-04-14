package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.time.LocalDateTime; // Imported LocalDateTime
import java.util.UUID;

@Data
public class PositionDataDto {
    private UUID positionDataId; // Changed from activityId to positionDataId
    private UUID activityId;
    private LocalDateTime timestamp; // Changed from Long to LocalDateTime
    private Double latitude; // Changed from Long to Double
    private Double longitude; // Changed from Long to Double
    private Integer height;
}