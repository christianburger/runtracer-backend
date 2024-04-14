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
@Table("activity")
public class Activity {

    @Id
    private UUID activityId;

    @Column("user_id")
    private UUID userId;

    @Column("activity_type")
    private String activityType;

    @Column("start_time")
    private LocalDateTime startTime; // Changed to LocalDateTime

    @Column("end_time")
    private LocalDateTime endTime; // Changed to LocalDateTime
}
