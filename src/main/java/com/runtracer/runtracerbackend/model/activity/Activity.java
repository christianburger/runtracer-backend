package com.runtracer.runtracerbackend.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Activity {

    @Id
    private UUID id;

    @Column("user_id")
    private Long userId;

    @Column("position_data")
    private List<PositionData> positionData;

    @Column("heartbeat_data")
    private List<HeartbeatData> heartbeatData;

    @Column("movement_data")
    private List<MovementData> movementData;

    @Column("step_data")
    private List<StepData> stepData;
}