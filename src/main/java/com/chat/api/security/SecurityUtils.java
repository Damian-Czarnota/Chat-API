package com.chat.api.security;

import com.chat.api.infrastructure.model.User;
import com.chat.api.infrastructure.model.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    public static User getCurrentUser() {
        return ((UserPrinciple) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getUser();
    }

}
