package com.runtracer.runtracerbackend;

import com.runtracer.runtracerbackend.controller.AuthenticationController;
import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.exceptions.InvalidCredentialsException;
import com.runtracer.runtracerbackend.exceptions.UserNotFoundException;
import com.runtracer.runtracerbackend.mappers.RoleMapper;
import com.runtracer.runtracerbackend.mappers.UserMapper;
import com.runtracer.runtracerbackend.mappers.UserRoleMapper;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.repository.RoleRepository;
import com.runtracer.runtracerbackend.repository.UserRepository;
import com.runtracer.runtracerbackend.repository.UserRoleRepository;
import com.runtracer.runtracerbackend.service.UserService;
import com.runtracer.runtracerbackend.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthenticationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private UserRoleMapper userRoleMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private AuthenticationController authenticationController;

    @BeforeEach
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, roleRepository, userRoleRepository, userMapper, roleMapper, userRoleMapper, passwordEncoder);
        authenticationController = new AuthenticationController(userService, passwordEncoder, null);
    }

    @Test
    public void loginWithExistingUser() {
        log.info("loginWithExistingUser");
        when(userMapper.toDto(any(User.class))).thenAnswer(i -> new UserDto());
        User user = new User();
        user.setUsername("existingUser");
        log.info("Created User with username: {}", user.getUsername());

        String rawPassword = "password";
        log.info("Raw password: {}", rawPassword);

        String encodedPassword = passwordEncoder.encode(rawPassword); // encode the password
        log.info("Encoded password: {}", encodedPassword);
        user.setPassword(encodedPassword);

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(rawPassword); // use raw password here
        log.info("Created UserDto with username: {} and password: {}", userDto.getUsername(), userDto.getPassword());

        AuthenticationController.LoginForm loginForm = new AuthenticationController.LoginForm();
        loginForm.setUsername(user.getUsername());
        loginForm.setPassword(rawPassword); // and here
        log.info("Created LoginForm with username: {} and password: {}", loginForm.getUsername(), loginForm.getPassword());

        when(userRepository.findByUsername(loginForm.getUsername())).thenReturn(Mono.just(user));
        log.info("Mocked UserRepository to return User for username: {}", loginForm.getUsername());

        String response = authenticationController.login(loginForm).block();
        log.info("Response from login: {}", response);

        assertEquals("Login successful", response);
        log.info("Assertion passed: response equals 'Login successful'");
    }

    @Test
    public void loginWithNonExistentUser() {
        when(userRepository.findByUsername(anyString())).thenReturn(Mono.empty());

        AuthenticationController.LoginForm loginForm = new AuthenticationController.LoginForm();
        loginForm.setUsername("nonExistentUser");
        loginForm.setPassword("password");

        assertThrows(UserNotFoundException.class, () -> authenticationController.login(loginForm).block());
    }

    @Test
    public void loginWithInvalidPassword() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword(passwordEncoder.encode("correctPassword"));

        when(userRepository.findByUsername("testUser")).thenReturn(Mono.just(user));

        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("invalidPassword");

        assertThrows(InvalidCredentialsException.class, () -> userService.authenticateFromDto(userDto).block());
    }

    @Test
    public void authenticateFromDto_ValidCredentials() {
        UserDto userDto = new UserDto();
        userDto.setUsername("existingUser");
        userDto.setPassword("password");

        when(userMapper.toDto(any(User.class))).thenAnswer(i -> userDto);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Mono.just(user));

        Mono<UserDto> result = userService.authenticateFromDto(userDto);

        StepVerifier.create(result)
                .expectNextMatches(returnedUserDto -> returnedUserDto.getUsername().equals(userDto.getUsername()))
                .verifyComplete();
    }

    @Test
    public void authenticateFromDto_InvalidCredentials() {
        UserDto userDto = new UserDto();
        userDto.setUsername("existingUser");
        userDto.setPassword("wrongPassword");

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode("password"));

        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Mono.just(user));

        Mono<UserDto> result = userService.authenticateFromDto(userDto);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof InvalidCredentialsException)
                .verify();
    }

    @Test
    public void authenticateFromDto_NonExistentUser() {
        log.info("authenticateFromDto_NonExistentUser");

        when(userMapper.toDto(any(User.class))).thenAnswer(i -> new UserDto());

        User user = new User();
        user.setUsername("nonExistentUser");
        user.setPassword("password");
        log.info("Created User with username: {} and password: {}", user.getUsername(), user.getPassword());

        UserDto userDto = userMapper.toDto(user); // Use the stubbing here
        log.info("Converted User to UserDto: {}", userDto);

        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(Mono.empty());
        log.info("Mocked UserRepository to return empty Mono for username: {}", userDto.getUsername());

        Mono<UserDto> result = userService.authenticateFromDto(userDto);
        log.info("Called authenticateFromDto with UserDto: {}", userDto);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof UserNotFoundException)
                .verify();
        log.info("Assertion passed: UserNotFoundException is thrown");
    }


}