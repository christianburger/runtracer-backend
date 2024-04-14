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
@Table("heartbeat_data")
public class HeartbeatData {
    @Id
    private UUID heartbeatDataId;
    @Column("timestamp")
    private LocalDateTime timestamp; // Changed from Long to LocalDateTime
    @Column("heartbeat")
    private Integer heartbeat;
    @Column("activity_id")
    private UUID activityId;
}