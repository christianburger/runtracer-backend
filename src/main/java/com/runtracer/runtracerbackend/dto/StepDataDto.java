package com.runtracer.runtracerbackend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StepDataDto {
    private UUID activityId;
    private Long timestamp;
    private Integer steps;
}