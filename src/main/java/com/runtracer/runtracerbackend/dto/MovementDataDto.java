package com.runtracer.runtracerbackend.dto;

import lombok.Data;

@Data
public class MovementDataDto {
    private Long timestamp;
    private Integer up;
    private Integer down;
    private Integer left;
    private Integer right;
}