package com.chat.api.infrastructure.dto;

import com.chat.api.infrastructure.enums.RoleName;
import com.chat.api.infrastructure.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {

    private Long ID;
    private String name;
    private String email;
    private String username;
    private byte[] profileImage;
    private Set<RoleDto> roles;
    private Boolean isAdmin;

    public UserDto(User user) {
        this.ID = user.getID();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.profileImage = user.getProfileImage();
        this.roles = user.getRoles().stream().map(role -> new RoleDto(role.getRoleName())).collect(Collectors.toSet());
        this.isAdmin = isAdmin();
    }

    private Boolean isAdmin() {
        return this.roles.contains(RoleName.ROLE_ADMIN);
    }

}
