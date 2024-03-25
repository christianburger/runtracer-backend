package com.runtracer.runtracerbackend.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepData {
    private Long timestamp;
    private Integer steps;
}