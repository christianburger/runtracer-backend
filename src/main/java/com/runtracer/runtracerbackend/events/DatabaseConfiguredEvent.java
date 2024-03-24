package com.runtracer.runtracerbackend.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DatabaseConfiguredEvent extends ApplicationEvent {
    public DatabaseConfiguredEvent(Object source) {
        super(source);
    }
}