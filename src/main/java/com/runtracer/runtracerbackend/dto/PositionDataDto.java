package com.runtracer.runtracerbackend.dto;

import lombok.Data;

@Data
public class PositionDataDto {
    private Long activityId;
    private Long timestamp;
    private Long latitude;
    private Long longitude;
    private Integer height;
}