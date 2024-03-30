package com.runtracer.runtracerbackend.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ApiUserResponse {
    private List<UserDto> results;

    @Data
    public static class UserDto {
        private Long id;
        private String username;
        private String password;
        private String email;
        private List<String> roles;

        @JsonProperty("isAccountNonExpired")
        private boolean accountNonExpired;

        @JsonProperty("isAccountNonLocked")
        private boolean accountNonLocked;

        @JsonProperty("isCredentialsNonExpired")
        private boolean credentialsNonExpired;

        @JsonProperty("isEnabled")
        private boolean enabled;
    }
}