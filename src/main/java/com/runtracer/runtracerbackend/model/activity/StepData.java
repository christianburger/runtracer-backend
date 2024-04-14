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
@Table("step_data")
public class StepData {
    @Id
    private UUID stepDataId;
    @Column("timestamp")
    private LocalDateTime timestamp; // Changed from Long to LocalDateTime
    @Column("steps")
    private Integer steps;
    @Column("activity_id")
    private UUID activityId;
}