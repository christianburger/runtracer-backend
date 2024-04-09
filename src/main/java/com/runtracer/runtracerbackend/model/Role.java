package com.runtracer.runtracerbackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("roles")
public class Role implements GrantedAuthority {

    @Id
    private UUID roleId;
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