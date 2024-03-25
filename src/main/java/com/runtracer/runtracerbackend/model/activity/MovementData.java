package com.runtracer.runtracerbackend.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovementData {
    private Long timestamp;
    private Integer up;
    private Integer down;
    private Integer left;
    private Integer right;
}