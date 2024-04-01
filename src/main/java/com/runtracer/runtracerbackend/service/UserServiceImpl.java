package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.config.SecurityConfig;
import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.exceptions.InvalidCredentialsException;
import com.runtracer.runtracerbackend.mappers.*;
import com.runtracer.runtracerbackend.model.Role;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.model.UserRole;
import com.runtracer.runtracerbackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private StepDataRepository stepDataRepository;

    @Autowired
    private HeartbeatDataRepository heartbeatDataRepository;

    @Autowired
    private MovementDataRepository movementDataRepository;

    @Autowired
    private PositionDataRepository positionDataRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private PositionDataMapper positionDataMapper;

    @Autowired
    private HeartbeatDataMapper heartbeatDataMapper;

    @Autowired
    private MovementDataMapper movementDataMapper;

    @Autowired
    private StepDataMapper stepDataMapper;
    @Autowired
    private TransactionalOperator transactionalOperator;

    public Mono<UserDto> authenticateFromDto(UserDto userDto) {
        PasswordEncoder passwordEncoder = SecurityConfig.getPasswordEncoder();
        log.info("Authenticating user: {}", userDto.getUsername());

        return userRepository.findByUsername(userDto.getUsername())
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("User not found for username: {}", userDto.getUsername());
                    return Mono.error(new UsernameNotFoundException("User Not Found"));
                }))
                .flatMap(user -> {
                    log.info("Fetched user from database: {}", user);
                    boolean passwordMatches = passwordEncoder.matches(userDto.getPassword(), user.getPassword());
                    log.info("Password matches for username: {}: {}", userDto.getUsername(), passwordMatches);

                    if (!passwordMatches) {
                        log.error("Password does not match for username: {}", userDto.getUsername());
                        return Mono.error(new InvalidCredentialsException());
                    }
                    log.info("Password matched for username: {}", userDto.getUsername());
                    return Mono.just(userMapper.toDto(user));
                })
                .doOnEach(signal -> {
                    if (signal.hasError()) {
                        log.error("Error occurred during authentication: ", signal.getThrowable());
                    } else if (signal.isOnComplete()) {
                        log.info("Authentication completed without error");
                    }
                });
    }

    public Mono<UserDto> findByIdDto(Long id) {
        log.info("Fetching user with id: {}", id);
        Mono<UserDto> userDtoMono = Mono.empty();
        log.info("Fetched user: {}", userDtoMono);
        return userDtoMono;
    }

    public Mono<UserDto> updateDto(Long id, UserDto userDto) {
        log.info("Updating user with id: {} with data: {}", id, userDto);
        Mono<UserDto> updatedUserDtoMono = Mono.empty();
        log.info("Updated user: {}", updatedUserDtoMono);
        return updatedUserDtoMono;
    }

    public Flux<UserDto> findAllDto() {
        log.info("Fetching all users");
        Flux<UserDto> usersFlux = Flux.empty();
        log.info("Fetched users: {}", usersFlux);
        return usersFlux;
    }

    private Mono<User> saveEntitiesFromDto(UserDto userDto) {
        log.info("Extracting User from UserDto: {}", userDto);
        User user = userMapper.toEntity(userDto);
        Role role = roleMapper.toEntity(userDto.getRoles().get(0));
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(role.getRoleId());

        log.info("Extracted User: {}", user);
        log.info("Extracted Role: {}", role);
        log.info("Extracted UserRole: {}", userRole);

        log.info("User UUID: {}", user.getUserId());
        log.info("Role UUID: {}", role.getRoleId());

        Mono<User> savedUser = this.save(user);
        Mono<Role> savedRole = this.saveRole(role);
        Mono<UserRole> savedUserRole = this.saveUserRole(userRole);

        log.info("Saved User: {}", savedUser);
        log.info("Saved Role: {}", savedRole);
        log.info("Saved UserRole: {}", savedUserRole);

        return savedUser;
    }

    public Mono<User> save(User user) {
        log.info("Saving User: {}", user);
        return userRepository.save(user)
                .doOnNext(savedUser -> log.info("Saved User: {}", savedUser))
                .doOnError(e -> log.error("Error occurred while saving User: ", e));
    }

    private Mono<Role> saveRole(Role role) {
        log.info("Saving Role: {}", role);
        return roleRepository.save(role)
                .doOnNext(savedRole -> log.info("Saved Role: {}", savedRole))
                .doOnError(e -> log.error("Error occurred while saving Role: ", e));
    }

    public Mono<UserRole> saveUserRole(UserRole userRole) {
        log.info("Saving UserRole: {}", userRole);
        return userRoleRepository.save(userRole)
                .doOnNext(savedUserRole -> log.info("Saved UserRole: {}", savedUserRole))
                .doOnError(e -> log.error("Error occurred while saving UserRole: ", e));
    }

    @Override
    public Mono<UserDto> saveDto(UserDto userDto) {
        log.info("Starting saveDto method with UserDto: {}", userDto);

        return transactionalOperator.transactional(saveEntitiesFromDto(userDto)
                        .flatMap(user -> Mono.just(userMapper.toDto(user))))
                .doOnNext(savedUserDto -> log.info("Finished saveDto method with UserDto: {}", savedUserDto))
                .doOnError(e -> log.error("Error occurred during saveDto method: ", e));
    }

    public Mono<Void> deleteByIdDto(Long id) {
        log.info("Deleting user with id: {}", id);
        Mono<Void> deletedUserMono = Mono.empty();
        log.info("Deleted user with id: {}", id);
        return deletedUserMono;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        log.info("Fetching user by username: {}", username);

        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User Not Found"))))
                .flatMap(user -> {
                    return roleRepository.findRolesByUserId(user.getUserId())
                            .collectList()
                            .doOnNext(user::setRoles)
                            .thenReturn(user);
                })
                .cast(UserDetails.class)
                .doOnNext(userDetails -> log.info("Fetched user: {}", userDetails));
    }
}