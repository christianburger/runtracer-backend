package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.activity.PositionData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionDataRepository extends R2dbcRepository<PositionData, Long> {
}