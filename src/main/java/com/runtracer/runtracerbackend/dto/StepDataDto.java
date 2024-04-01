package com.runtracer.runtracerbackend.dto;

import lombok.Data;

@Data
public class StepDataDto {
    private Long activityId;
    private Long timestamp;
    private Integer steps;
}