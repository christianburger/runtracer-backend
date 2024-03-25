package com.runtracer.runtracerbackend.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionData {
    private Long timestamp;
    private Long latitude;
    private Long longitude;
    private Integer height;
}