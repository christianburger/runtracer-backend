package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.time.LocalDateTime; // Imported LocalDateTime
import java.util.UUID;

@Data
public class HeartbeatDataDto {
    private UUID heartbeatDataId; // Changed from activityId to heartbeatDataId
    private UUID activityId;
    private LocalDateTime timestamp; // Changed from Long to LocalDateTime
    private Integer heartbeat;
}