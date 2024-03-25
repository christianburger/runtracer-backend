package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public <S extends User> Mono<S> save(S entity) {
        return userRepository.findByUsername(entity.getUsername())
                .hasElement()
                .flatMap(usernameExists -> {
                    if (usernameExists) {
                        return Mono.error(new RuntimeException("Username already exists"));
                    } else {
                        return userRepository.findByEmail(entity.getEmail())
                                .hasElement()
                                .flatMap(emailExists -> {
                                    if (emailExists) {
                                        return Mono.error(new RuntimeException("Email already exists"));
                                    } else {
                                        return userRepository.save(entity);
                                    }
                                });
                    }
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Flux<User> findByUsernameAndEmail(String username, String email) {
        return userRepository.findByUsernameAndEmail(username, email);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).cast(UserDetails.class);
    }
}