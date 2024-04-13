package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.config.SecurityConfig;
import com.runtracer.runtracerbackend.dto.ActivityDto;
import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.mappers.ActivityMapper;
import com.runtracer.runtracerbackend.mappers.ApiUserResponseMapper;
import com.runtracer.runtracerbackend.mappers.RoleMapper;
import com.runtracer.runtracerbackend.mappers.UserMapper;
import com.runtracer.runtracerbackend.model.Role;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.model.UserRole;
import com.runtracer.runtracerbackend.model.activity.Activity;
import com.runtracer.runtracerbackend.repository.ActivityRepository;
import com.runtracer.runtracerbackend.repository.RoleRepository;
import com.runtracer.runtracerbackend.repository.UserRepository;
import com.runtracer.runtracerbackend.repository.UserRoleRepository;
import com.runtracer.runtracerbackend.service.ActivityService;
import com.runtracer.runtracerbackend.service.RoleService;
import com.runtracer.runtracerbackend.service.UserService;
import com.runtracer.runtracerbackend.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("postgresql-flyway-dev")
@Slf4j
public class ActivityControllerEndToEndTest {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    private WebTestClient webTestClient;

    private ActivityService activityService;

    private ActivityMapper activityMapper;
    private ApiUserResponseMapper apiUserResponseMapper;

    @Autowired
    public ActivityControllerEndToEndTest(ActivityService activityService, UserService userService, RoleService roleService, ActivityMapper activityMapper, ApiUserResponseMapper apiUserResponseMapper, RoleMapper roleMapper, UserMapper userMapper, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, ActivityRepository activityRepository) {
        this.activityService = activityService;
        this.userService = userService;
        this.roleService = roleService;
        this.activityMapper = activityMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.apiUserResponseMapper = apiUserResponseMapper;
        this.passwordEncoder = SecurityConfig.getPasswordEncoder();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.activityRepository = activityRepository;
    }

    @Test
    public void testCheckAndCreateUserInRepository() {
        TestUtils testUtils = new TestUtils(apiUserResponseMapper); // Instantiate TestUtils
        UserDto userDto = testUtils.createUserDto(); // Use TestUtils to create a UserDto
        log.info("Created UserDto: {}", userDto);

        // Convert UserDto to User using UserMapper
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        log.info("Converted UserDto to User: {}", user);

        // Save the user to the repository and assert that the user was saved correctly
        StepVerifier.create(userService.save(user)
                        .then(userService.findByUsernameUser(user.getUsername())))
                .assertNext(savedUser -> {
                    assertEquals("Username should match", user.getUsername(), savedUser.getUsername());
                    assertTrue(passwordEncoder.matches(userDto.getPassword(), savedUser.getPassword()), "Passwords should match");
                    log.info("User saved correctly: {}", savedUser);
                })
                .expectComplete()
                .verify();        // Check if the user exists before trying to update it

        User savedUser = userService.findByUsernameUser(user.getUsername()).block();
        log.info("User exists: id: " + savedUser.getUserId());
        log.info("User exists: username: " + savedUser.getUsername());
        log.info("User exists: email: " + savedUser.getEmail());
        log.info("User exists: roles: " + savedUser.getRoles());

        List<UserRole> userRoles = userRoleRepository.findByUserId(savedUser.getUserId()).collectList().block();
        log.info("User roles: {}", userRoles);

        if (userRoles.isEmpty()) {
            throw new RuntimeException("No roles found for user");
        }

        Role savedRole = roleService.findById(userRoles.get(0).getRoleId()).block();
        log.info("Role: {}", savedRole);
        assertEquals("Role should be " + userDto.getRoles().get(0).getName(), userDto.getRoles().get(0).getName(), savedUser.getRoles().get(0).getName());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetActivity() {
        TestUtils testUtils = new TestUtils(apiUserResponseMapper); // Instantiate TestUtils
        UserDto userDto = testUtils.createUserDto(); // Use TestUtils to create a UserDto
        log.info("Created UserDto: {}", userDto);

        // Convert UserDto to User using UserMapper
        User user = userMapper.toEntity(userDto);
        log.info("Converted UserDto to User: {}", user);

        // Save the user and its roles using UserService
        userService.save(user)
                .doOnNext(savedUser -> log.info("Saved User with ID: {}", savedUser.getUserId()))
                .doOnError(e -> log.error("Error occurred while saving user: ", e))
                .subscribe(savedUser -> {
                    UUID userId = savedUser.getUserId(); // Get the ID of the saved user

                    // Create ActivityDto using TestUtils
                    ActivityDto activityDto = testUtils.createActivityDto(userId);
                    log.info("Created ActivityDto: {}", activityDto);

                    // Convert ActivityDto to Activity using ActivityMapper
                    Activity activity = activityMapper.toEntity(activityDto);
                    log.info("Converted ActivityDto to Activity: {}", activity);

                    // Save the activity after the User is saved
                    activityService.save(activity)
                            .doOnNext(savedActivity -> log.info("Saved Activity with ID: {}", savedActivity.getActivityId()))
                            .doOnError(e -> log.error("Error occurred while saving activity: ", e))
                            .subscribe(savedActivity -> {
                                UUID activityId = savedActivity.getActivityId(); // Get the ID of the saved activity

                                ActivityDto expectedActivityDto = new ActivityDto();
                                expectedActivityDto.setActivityId(activityId);
                                log.info("Expected ActivityDto: {}", expectedActivityDto);

                                webTestClient.get()
                                        .uri("/api/activities/{id}", activityId)
                                        .exchange()
                                        .expectStatus().isOk()
                                        .expectBody(ActivityDto.class)
                                        .consumeWith(response -> {
                                            ActivityDto result = response.getResponseBody();
                                            log.info("Result: {}", result);
                                        });
                            });
                });
    }
}