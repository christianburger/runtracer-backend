package com.runtracer.runtracerbackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("roles")
public class Role implements GrantedAuthority {

    @Id
    private Long roleId;
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