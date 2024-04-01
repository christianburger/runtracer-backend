package com.runtracer.runtracerbackend.service;

import com.runtracer.runtracerbackend.model.activity.Activity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ActivityService {
    Mono<Activity> findById(Long id);
    Flux<Activity> findAll();
    <S extends Activity> Mono<S> save(S entity);
    Mono<Activity> update(Long id, Activity entity);
    Mono<Void> deleteById(Long id);
}