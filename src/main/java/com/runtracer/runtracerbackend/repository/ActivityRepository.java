package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.activity.Activity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends R2dbcRepository<Activity, Long> {
}