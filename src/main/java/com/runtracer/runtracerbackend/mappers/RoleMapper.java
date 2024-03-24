package com.runtracer.runtracerbackend.mappers;

import com.runtracer.runtracerbackend.model.Role;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Mapper
public interface RoleMapper {
    default GrantedAuthority roleToGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getName());
    }
}