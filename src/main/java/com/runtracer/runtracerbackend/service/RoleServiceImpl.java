package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.Role;
import com.runtracer.runtracerbackend.repository.RoleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Mono<Role> findByName(String name) {
        return roleRepository.findById(name);
    }

    @Override
    public Flux<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public <S extends Role> Mono<S> save(S entity) {
        return roleRepository.save(entity);
    }

    @Override
    public Mono<Void> deleteByName(String name) {
        return roleRepository.deleteById(name);
    }
}