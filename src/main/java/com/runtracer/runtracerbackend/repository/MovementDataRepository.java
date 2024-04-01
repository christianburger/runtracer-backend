package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.activity.MovementData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementDataRepository extends R2dbcRepository<MovementData, Long> {
}