// File: ./service/RoleService.java
package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.Role;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {
    Mono<Role> findByName(String name);
    Flux<Role> findAll();
    <S extends Role> Mono<S> save(S entity);
    Mono<Void> deleteByName(String name);
}