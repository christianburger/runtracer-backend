package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.model.UserRole;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService extends ReactiveUserDetailsService {
    Mono<UserDto> authenticateFromDto(UserDto userDto);
    Mono<UserDto> findByIdDto(UUID id);
    Flux<UserDto> findAllDto();
    Mono<UserDto> saveDto(UserDto userDto);
    Mono<UserDto> updateDto(UUID id, UserDto userDto);
    Mono<Void> deleteByIdDto(UUID id);
    Mono<User> save(User user);
    Mono<UserRole> saveUserRole(UserRole userRole);
    Mono<UserDetails> findByUsername(String username);
}