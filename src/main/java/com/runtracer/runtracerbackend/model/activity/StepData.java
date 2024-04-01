package com.runtracer.runtracerbackend.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Getter
@Setter
@Table("step_data")
public class StepData {
    @Id
    private Long stepDataId;
    @Column("timestamp")
    private Long timestamp;
    @Column("steps")
    private Integer steps;
    @Column("activity_id")
    private Long activityId;
}