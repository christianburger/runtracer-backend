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
@Table("step_data")
public class StepData {
    @Id
    private UUID stepDataId;
    @Column("timestamp")
    private Long timestamp;
    @Column("steps")
    private Integer steps;
    @Column("activity_id")
    private UUID activityId;
}