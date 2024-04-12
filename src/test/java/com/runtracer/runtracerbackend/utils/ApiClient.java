package com.runtracer.runtracerbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class ApiClient {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ApiUserResponse getUserData() throws Exception {
        log.info("getUserData called");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://randomapi.com/api/7f0def00cce4f7a98a1bc19f567438d2"))
                .build();

        log.info("Sending request: {}", request);
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Received response: {}", response);

        ApiUserResponse apiUserResponse = MAPPER.readValue(response.body(), ApiUserResponse.class);
        log.info("Parsed response body into ApiUserResponse: {}", apiUserResponse);

        return apiUserResponse;
    }
}