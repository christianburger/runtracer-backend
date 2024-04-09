package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface RoleRepository extends R2dbcRepository<Role, UUID> {
    @Query("SELECT r.* FROM roles r INNER JOIN user_roles ur ON r.role_id = ur.role_id WHERE ur.user_id = :userId")
    Flux<Role> findRolesByUserId(UUID userId);
}