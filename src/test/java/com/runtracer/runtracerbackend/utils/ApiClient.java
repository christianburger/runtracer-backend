package com.runtracer.runtracerbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public ApiUserResponse getUserData() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://randomapi.com/api/7f0def00cce4f7a98a1bc19f567438d2"))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return MAPPER.readValue(response.body(), ApiUserResponse.class);
    }
}