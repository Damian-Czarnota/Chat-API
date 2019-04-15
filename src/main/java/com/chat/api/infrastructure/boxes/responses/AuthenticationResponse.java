package com.chat.api.infrastructure.boxes.responses;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class AuthenticationResponse {
    private String token;
    private String type = "Bearer";
    private boolean isAdmin;
    private boolean isUser;

    public AuthenticationResponse(String accessToken, Collection<? extends GrantedAuthority> authorities) {
        this.token = accessToken;
        this.isAdmin = setIsAdmin(authorities);
        this.isUser = !this.isAdmin;
    }

    private boolean setIsAdmin(Collection<? extends GrantedAuthority> authorities) {
       return authorities.stream()
                .anyMatch(auth -> ((GrantedAuthority) auth).getAuthority().equals("ROLE_ADMIN"));
    }
}
