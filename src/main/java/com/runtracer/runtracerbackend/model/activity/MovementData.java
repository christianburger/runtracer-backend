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
@Table("movement_data")
public class MovementData {
    @Id
    private Long movementDataId;
    @Column("timestamp")
    private Long timestamp;
    @Column("move_up")
    private Integer moveUp;
    @Column("move_down")
    private Integer moveDown;
    @Column("move_left")
    private Integer moveLeft;
    @Column("move_right")
    private Integer moveRight;
    @Column("activity_id")
    private Long activityId;
}