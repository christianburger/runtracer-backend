package com.runtracer.runtracerbackend.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime; // Imported LocalDateTime
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
    private LocalDateTime timestamp; // Changed from Long to LocalDateTime
    @Column("latitude")
    private Double latitude; // Changed from Long to Double
    @Column("longitude")
    private Double longitude; // Changed from Long to Double
    @Column("height")
    private Integer height;
    @Column("activity_id")
    private UUID activityId;
}