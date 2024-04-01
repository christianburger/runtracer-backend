package com.runtracer.runtracerbackend.dto;

import lombok.Data;

@Data
public class MovementDataDto {
    private Long activityId;
    private Long timestamp;
    private Integer moveUp;
    private Integer moveDown;
    private Integer moveLeft;
    private Integer moveRight;
}