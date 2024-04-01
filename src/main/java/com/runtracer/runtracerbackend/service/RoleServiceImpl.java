package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.dto.RoleDto;
import com.runtracer.runtracerbackend.model.Role;
import com.runtracer.runtracerbackend.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Mono<RoleDto> findByNameDto(String name) {
        // Implementation goes here
        return Mono.empty();
    }

    @Override
    public Flux<RoleDto> findAllDto() {
        // Implementation goes here
        return Flux.empty();
    }

    @Override
    public Mono<RoleDto> saveDto(RoleDto roleDto) {
        // Implementation goes here
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteByNameDto(String name) {
        // Implementation goes here
        return Mono.empty();
    }

    @Override
    public Mono<Role> save(Role role) {
        log.info("Saving role: {}", role);

        return roleRepository.save(role)
                .doOnSuccess(savedRole -> log.info("Saved role: {}", savedRole))
                .doOnError(e -> log.error("Error occurred while saving role: ", e));
    }
}