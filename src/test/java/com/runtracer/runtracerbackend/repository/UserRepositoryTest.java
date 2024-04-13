package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.mappers.ApiUserResponseMapper;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("postgresql-flyway-dev")
public class UserRepositoryTest {

    private final ApiUserResponseMapper apiUserResponseMapper;

    @Autowired
    public UserRepositoryTest() {
        this.apiUserResponseMapper = Mappers.getMapper(ApiUserResponseMapper.class);
    }

    private Stream<UserDto> provideUsers() {
        ApiUserResponseMapper apiUserResponseMapper = Mappers.getMapper(ApiUserResponseMapper.class);
        TestUtils testUtils = new TestUtils(apiUserResponseMapper);
        return Stream.of(
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto(),
                testUtils.createUserDto()
        );
    }

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCreateAndFindUserDto() {
        TestUtils testUtils = new TestUtils(apiUserResponseMapper);
        UserDto userDto = testUtils.createUserDto();

        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        String username = user.getUsername();
        assertFalse(username.isEmpty());
    }

///*    @Test
//    public void testCreateAndPersistAndFindUserInRepository() {
//        // Arrange
//        TestUtils testUtils = new TestUtils(apiUserResponseMapper);
//        UserDto userDto = testUtils.createUserDto();
//
//        User user = new User();
//        user.setUserId(userDto.getUserId());
//        user.setUsername(userDto.getUsername());
//        user.setPassword(userDto.getPassword());
//
//        // Act
//        Mono<User> savedUser = userRepository.save(user);
//
//        // Assert
//        StepVerifier.create(savedUser)
//                .expectNextMatches(saved -> saved.getUsername().equals(user.getUsername()))
//                .verifyComplete();
//
//        // Act
//        Mono<User> foundUser = userRepository.findByUsername(user.getUsername());
//
//        // Assert
//        StepVerifier.create(foundUser)
//                .expectNextMatches(found -> found.getUsername().equals(user.getUsername()))
//                .verifyComplete();
//    }*/
}

    /*

    @Test
    public void testSaveAndFindUser() {
        // Arrange
        ApiUserResponseMapper apiUserResponseMapper = Mappers.getMapper(ApiUserResponseMapper.class);
        TestUtils testUtils = new TestUtils(apiUserResponseMapper);
        UserDto userDto = testUtils.createUserDto();

        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        // Act
        Mono<User> savedUser = userRepository.save(user);

        // Assert
        StepVerifier.create(savedUser)
                .expectNextMatches(saved -> saved.getUsername().equals(user.getUsername()))
                .verifyComplete();

        // Act
        Mono<User> foundUser = userRepository.findByUsername(user.getUsername());

        // Assert
        StepVerifier.create(foundUser)
                .expectNextMatches(found -> found.getUsername().equals(user.getUsername()))
                .verifyComplete();
    }
*/