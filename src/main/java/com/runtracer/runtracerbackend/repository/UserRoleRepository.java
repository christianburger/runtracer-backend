package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.UserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends R2dbcRepository<UserRole, Long> {
}