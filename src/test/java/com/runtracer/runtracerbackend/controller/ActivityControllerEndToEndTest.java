package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.mappers.UserMapper;
import com.runtracer.runtracerbackend.repository.UserRepository;
import com.runtracer.runtracerbackend.service.UserService;
import com.runtracer.runtracerbackend.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ActivityControllerEndToEndTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testEndToEndUserFlow() {
        // Create a new user
        UserDto newUserDto = TestUtils.createUserDto();

        // Save the new user through the controller
        UserDto savedUserDto = userController.createUser(newUserDto).block();

        // Retrieve the user from the controller
        UserDto userFromController = userController.getUser(savedUserDto.getUserId()).block();

        // Assert that the user saved and the user retrieved from the controller are the same
        assertEquals(savedUserDto, userFromController);
    }
}