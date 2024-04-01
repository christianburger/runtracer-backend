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
@Table("heartbeat_data")
public class HeartbeatData {
    @Id
    private Long heartbeatDataId;
    @Column("timestamp")
    private Long timestamp;
    @Column("heartbeat")
    private Integer heartbeat;
    @Column("activity_id")
    private Long activityId;
}