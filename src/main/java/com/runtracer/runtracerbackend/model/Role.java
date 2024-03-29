package com.runtracer.runtracerbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    private Long id;
    private RoleType name;

    @Override
    public String getAuthority() {
        return name.toString();
    }

    public void setAuthority(String authority) {
        this.name = RoleType.valueOf(authority);
    }

    public enum RoleType {
        ROLE_USER,
        ROLE_ADMIN
    }
}