package com.runtracer.runtracerbackend.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@Getter
@Setter
@Table("position_data")
public class PositionData {
    @Id
    private UUID positionDataId;
    @Column("timestamp")
    private Long timestamp;
    @Column("latitude")
    private Long latitude;
    @Column("longitude")
    private Long longitude;
    @Column("height")
    private Integer height;
    @Column("activity_id")
    private UUID activityId;
}