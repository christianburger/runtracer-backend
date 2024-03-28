package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.activity.Activity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ActivityService {
    Mono<Activity> findById(UUID id);
    Flux<Activity> findAll();
    <S extends Activity> Mono<S> save(S entity);
    Mono<Void> deleteById(UUID id);
}