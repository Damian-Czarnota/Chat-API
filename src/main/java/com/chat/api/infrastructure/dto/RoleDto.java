package com.chat.api.infrastructure.dto;

import com.chat.api.infrastructure.enums.RoleName;
import lombok.Data;

@Data
public class RoleDto {
    private RoleName roleName;

    RoleDto(RoleName roleName) {
        this.roleName = roleName;
    }
}
