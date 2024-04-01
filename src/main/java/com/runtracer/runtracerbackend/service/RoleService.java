package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.dto.RoleDto;
import com.runtracer.runtracerbackend.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {
    Mono<RoleDto> findByNameDto(String name);
    Flux<RoleDto> findAllDto();
    Mono<RoleDto> saveDto(RoleDto roleDto);
    Mono<Void> deleteByNameDto(String name);
    Mono<Role> save(Role role);
}