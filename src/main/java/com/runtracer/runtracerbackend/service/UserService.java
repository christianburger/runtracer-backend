package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.model.UserRole;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {
    Mono<UserDto> authenticateFromDto(UserDto userDto);
    Mono<UserDto> findByIdDto(Long id);
    Flux<UserDto> findAllDto();
    Mono<UserDto> saveDto(UserDto userDto);
    Mono<UserDto> updateDto(Long id, UserDto userDto);
    Mono<Void> deleteByIdDto(Long id);
    Mono<User> save(User user);
    Mono<UserRole> saveUserRole(UserRole userRole);
}