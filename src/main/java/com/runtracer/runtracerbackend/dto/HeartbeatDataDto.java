package com.runtracer.runtracerbackend.dto;

import lombok.Data;

@Data
public class HeartbeatDataDto {
    private Long timestamp;
    private Integer heartbeat;
}