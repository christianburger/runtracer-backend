package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.activity.HeartbeatData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartbeatDataRepository extends R2dbcRepository<HeartbeatData, Long> {
}