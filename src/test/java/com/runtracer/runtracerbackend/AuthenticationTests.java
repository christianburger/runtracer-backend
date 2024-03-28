package com.runtracer.runtracerbackend;

import com.runtracer.runtracerbackend.controller.AuthenticationController;
import com.runtracer.runtracerbackend.exceptions.InvalidCredentialsException;
import com.runtracer.runtracerbackend.exceptions.UserNotFoundException;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationTests {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void loginWithValidCredentials() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("$2a$10$1q5OxYzubMLtb1sfn2JKqukXphkZWhfcZ.M5pQJY96iHMWcaY/dTm");

        when(userService.findByUsername("testUser")).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        AuthenticationController.LoginForm loginForm = new AuthenticationController.LoginForm();
        loginForm.setUsername("testUser");
        loginForm.setPassword("password");

        String response = authenticationController.login(loginForm).block();

        assertEquals("Login successful", response);
    }

    @Test
    public void loginWithNonExistentUser() {
        when(userService.findByUsername(anyString())).thenReturn(Mono.empty());

        AuthenticationController.LoginForm loginForm = new AuthenticationController.LoginForm();
        loginForm.setUsername("nonExistentUser");
        loginForm.setPassword("password");

        assertThrows(UserNotFoundException.class, () -> authenticationController.login(loginForm).block());
    }

    @Test
    public void loginWithInvalidPassword() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        when(userService.findByUsername("testUser")).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        AuthenticationController.LoginForm loginForm = new AuthenticationController.LoginForm();
        loginForm.setUsername("testUser");
        loginForm.setPassword("invalidPassword");

        assertThrows(InvalidCredentialsException.class, () -> authenticationController.login(loginForm).block());
    }
}