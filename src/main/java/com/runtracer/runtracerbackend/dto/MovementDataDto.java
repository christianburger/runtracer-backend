package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MovementDataDto {
    private UUID activityId;
    private Long timestamp;
    private Integer moveUp;
    private Integer moveDown;
    private Integer moveLeft;
    private Integer moveRight;
}