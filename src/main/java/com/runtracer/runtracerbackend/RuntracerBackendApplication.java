package com.runtracer.runtracerbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Arrays;

@Slf4j
@EnableWebFlux
@SpringBootApplication
public class RuntracerBackendApplication {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(RuntracerBackendApplication.class, args);
    }

    @EventListener
    public void onEventReceived(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            logAllBeans();
        }
    }

    private void logAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        log.info("All beans: {}", Arrays.toString(allBeanNames));
        for (String beanName : allBeanNames) {
            log.info("BEAN: Bean name: {}", beanName);
        }
    }
}