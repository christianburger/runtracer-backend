package com.runtracer.runtracerbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class RuntracerBackendApplication {

    @Autowired
    public RuntracerBackendApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(RuntracerBackendApplication.class, args);
    }

    @EventListener
    public void onEventReceived(ApplicationEvent event) {
        if (event != null) {
            log.info("Received event of type: " + event.getClass().getSimpleName());
        }
    }
}