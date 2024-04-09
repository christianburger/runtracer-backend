package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<User, UUID> {

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);

    @Query("SELECT * FROM User WHERE username = :username")
    Mono<User> loadUserByUsername(String username);

    @Query("SELECT * FROM users WHERE username LIKE :username AND email LIKE :email")
    Flux<User> findByUsernameAndEmail(String username, String email);

    @Override
    @NonNull
    <S extends User> Mono<S> save(@NonNull S entity);

    @Override
    @NonNull
    Mono<User> findById(@NonNull UUID id);

    @Override
    @NonNull
    Flux<User> findAll();

    @Override
    @NonNull
    Mono<Void> deleteById(@NonNull UUID id);
}