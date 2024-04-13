package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.config.SecurityConfig;
import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.exceptions.InvalidCredentialsException;
import com.runtracer.runtracerbackend.exceptions.UserAlreadyInDatabase;
import com.runtracer.runtracerbackend.exceptions.UserNotFoundException;
import com.runtracer.runtracerbackend.mappers.RoleMapper;
import com.runtracer.runtracerbackend.mappers.UserMapper;
import com.runtracer.runtracerbackend.mappers.UserRoleMapper;
import com.runtracer.runtracerbackend.model.Role;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.model.UserRole;
import com.runtracer.runtracerbackend.repository.RoleRepository;
import com.runtracer.runtracerbackend.repository.UserRepository;
import com.runtracer.runtracerbackend.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserRoleRepository userRoleRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, UserMapper userMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.passwordEncoder = SecurityConfig.getPasswordEncoder();
    }

    @Override
    public Mono<UserDto> authenticateFromDto(UserDto userDto) {
        return userRepository.findByUsername(userDto.getUsername())
                .switchIfEmpty(Mono.error(new UserNotFoundException())) // throw UserNotFoundException if user does not exist
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .map(userMapper::toDto)
                .switchIfEmpty(Mono.error(new InvalidCredentialsException())); // throw InvalidCredentialsException if password does not match
    }

    @Override
    public Mono<UserDto> findByIdDto(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> updateDto(UUID id, UserDto userDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDto.getUsername());
                    existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    existingUser.setEmail(userDto.getEmail());
                    existingUser.setGoogleId(userDto.getGoogleId());
                    existingUser.setImageUrl(userDto.getImageUrl());
                    return existingUser;
                })
                .flatMap(userRepository::save)
                .map(userMapper::toDto);
    }

    @Override
    public Flux<UserDto> findAllDto() {
        return userRepository.findAll()
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> saveDto(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user)
                .map(userMapper::toDto);
    }

    @Override
    public Mono<Void> deleteByIdDto(UUID id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<User> save(User user) {
        user.setUserId(null);
        return userRepository.findByUsername(user.getUsername())
                .hasElement()
                .flatMap(userExists -> {
                    if (userExists) {
                        return Mono.error(new UserAlreadyInDatabase());
                    } else {
                        return userRepository.save(user)
                                .flatMap(savedUser -> {
                                    log.info("Saved User: {}", savedUser);
                                    if (savedUser.getRoles() != null && !savedUser.getRoles().isEmpty()) {
                                        Role role = savedUser.getRoles().get(0);
                                        return saveRole(role)
                                                .flatMap(savedRole -> {
                                                    UserRole userRole = new UserRole();
                                                    userRole.setUserId(savedUser.getUserId());
                                                    userRole.setRoleId(savedRole.getRoleId());
                                                    return saveUserRole(userRole)
                                                            .then(userRoleRepository.findByUserId(savedUser.getUserId())
                                                                    .flatMap(userRole1 -> roleRepository.findById(userRole1.getRoleId()))
                                                                    .collectList()
                                                                    .doOnNext(savedUser::setRoles)
                                                                    .thenReturn(savedUser));
                                                });
                                    } else {
                                        log.error("No roles found for the user: {}", savedUser);
                                        return Mono.just(savedUser);
                                    }
                                });
                    }
                })
                .doOnError(error -> log.error("Error occurred: ", error));
    }

    @Override
    public Mono<Role> saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Mono<UserRole> saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> (UserDetails) user);
    }

    @Override
    public Mono<User> findByUsernameUser(String username) {
        log.info("findByUsernameUser: {}", username);

        return userRepository.findByUsername(username)
                .doOnNext(user -> log.info("User found: {}", user))
                .flatMap(user -> userRoleRepository.findByUserId(user.getUserId())
                        .collectList()
                        .doOnNext(userRoles -> log.info("User roles found: {}", userRoles))
                        .flatMap(userRoles -> Flux.fromIterable(userRoles)
                                .flatMap(userRole -> roleRepository.findById(userRole.getRoleId()))
                                .collectList()
                                .doOnNext(roles -> log.info("Roles found: {}", roles))
                                .map(roles -> {
                                    user.setRoles(roles);
                                    return user;
                                })
                        )
                )
                .doOnNext(user -> log.info("Final user: {}", user));
    }
}