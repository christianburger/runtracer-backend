package com.runtracer.runtracerbackend;

import com.runtracer.runtracerbackend.repository.ReactiveOAuth2ClientRegistrationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RuntracerBackendApplicationTests {

    @Autowired
    private ReactiveOAuth2ClientRegistrationRepository reactiveOAuth2ClientRegistrationRepository;

    @Test
    void contextLoads() {
    }
}