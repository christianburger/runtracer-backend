// File: ./UserRoleRepository.java
package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.UserRole;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface UserRoleRepository extends R2dbcRepository<UserRole, UUID> {
    @Query("SELECT * FROM user_roles WHERE user_id = :userId")
    Flux<UserRole> findByUserId(UUID userId);
}