package com.runtracer.runtracerbackend.repository;

import com.runtracer.runtracerbackend.config.FlywayTestConfiguration;
import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.mappers.ApiUserResponseMapper;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.utils.TestUtils;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.sql.DataSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ComponentScan(basePackages = "com.runtracer.runtracerbackend.repository")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {FlywayTestConfiguration.class})
@Import(FlywayTestConfiguration.class)
@SpringBootTest
public class UserRepositoryTest {


    @Autowired
    private FlywayTestConfiguration flywayTestConfiguration;
    @Autowired
    private Flyway flyway;

    private ApiUserResponseMapper apiUserResponseMapper;

    @Autowired
    public UserRepositoryTest(FlywayTestConfiguration flywayTestConfiguration, Flyway flyway) {
        this.flywayTestConfiguration = flywayTestConfiguration;
        this.apiUserResponseMapper = Mappers.getMapper(ApiUserResponseMapper.class);
        this.flyway = flyway;
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
        DataSource dataSource = flywayTestConfiguration.dataSource();
        flywayTestConfiguration.testConnection(dataSource);
        flyway.clean();
        flyway.migrate();
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
//    public void testCreateAndPersistAndFindUserDtoInRepository() {
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