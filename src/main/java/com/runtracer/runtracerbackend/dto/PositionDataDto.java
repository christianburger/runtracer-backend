package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PositionDataDto {
    private UUID activityId;
    private Long timestamp;
    private Long latitude;
    private Long longitude;
    private Integer height;
}