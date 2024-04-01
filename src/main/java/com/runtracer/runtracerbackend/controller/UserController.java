package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "UserController", description = "Operations pertaining to users in Runtracer")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get a user by its ID")
    @GetMapping("/{id}")
    public Mono<UserDto> getUser(@PathVariable Long id) {
        log.info("Fetching user with id: {}", id);
        Mono<UserDto> userDtoMono = userService.findByIdDto(id);
        log.info("Fetched user: {}", userDtoMono);
        return userDtoMono;
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Creating user: {}", userDto);
        Mono<UserDto> createdUserDtoMono = userService.saveDto(userDto);
        log.info("Created user: {}", createdUserDtoMono);
        return createdUserDtoMono;
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("/{id}")
    public Mono<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.info("Updating user with id: {} with data: {}", id, userDto);
        Mono<UserDto> updatedUserDtoMono = userService.updateDto(id, userDto);
        log.info("Updated user: {}", updatedUserDtoMono);
        return updatedUserDtoMono;
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public Flux<UserDto> getAllUsers() {
        log.info("Fetching all users");
        Flux<UserDto> usersFlux = userService.findAllDto();
        log.info("Fetched users: {}", usersFlux);
        return usersFlux;
    }

    @Operation(summary = "Delete a user by its ID")
    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with id: {}", id);
        Mono<Void> deletedUserMono = userService.deleteByIdDto(id);
        log.info("Deleted user with id: {}", id);
        return deletedUserMono;
    }
}