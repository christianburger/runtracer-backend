package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.activity.StepData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepDataRepository extends R2dbcRepository<StepData, Long> {
}