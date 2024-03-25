package com.runtracer.runtracerbackend;

import com.runtracer.runtracerbackend.controller.LoginController;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
public class LoginTests {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private UserService userService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        webTestClient = WebTestClient.bindToController(loginController).build();
    }

    @Test
    public void loginWithValidCredentials() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("$2a$10$1q5OxYzubMLtb1sfn2JKqukXphkZWhfcZ.M5pQJY96iHMWcaY/dTm");
        user.setEmail("test@example.com");
        when(userService.findByUsername("testUser")).thenReturn(Mono.just(user));

        webTestClient.post().uri("/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue("{\"username\": \"testUser\", \"password\": \"password\"}").exchange().expectStatus().isOk().expectHeader().exists(HttpHeaders.AUTHORIZATION);
    }

    @Test
    public void loginWithInvalidCredentials() {
        when(userService.findByUsername(anyString())).thenReturn(Mono.empty());

        webTestClient.post().uri("/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).bodyValue("{\"username\": \"invalidUser\", \"password\": \"invalidPassword\"}").exchange().expectStatus().isUnauthorized();
    }
}