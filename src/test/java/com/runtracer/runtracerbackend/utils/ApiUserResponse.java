package com.runtracer.runtracerbackend.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.runtracer.runtracerbackend.dtos.ApiRoleDto;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiUserResponse {
    private List<UserDto> results;

    @Data
    public static class UserDto {
        private UUID id;
        @JsonProperty("userId")
        private String userId;
        private String username;
        private String password;
        private String email;
        private List<ApiRoleDto> roles; // Changed from List<String> to List<ApiRoleDto>

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