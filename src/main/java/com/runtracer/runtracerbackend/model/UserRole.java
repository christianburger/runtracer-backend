package com.runtracer.runtracerbackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Getter
@Setter
@Table("user_roles")
public class UserRole {
    @Id
    @Column("user_role_id")
    private Long userRoleId;
    @Column("user_id")
    private Long userId;
    @Column("role_id")
    private Long roleId;
}