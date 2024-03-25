package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {
    Mono<User> findById(Long id);
    Flux<User> findAll();
    <S extends User> Mono<S> save(S entity);
    Mono<Void> deleteById(Long id);
    Flux<User> findByUsernameAndEmail(String username, String email);
}