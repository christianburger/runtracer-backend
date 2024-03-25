package com.runtracer.runtracerbackend.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TestEvent extends ApplicationEvent {
    public TestEvent(Object source) {
        super(source);
    }
}