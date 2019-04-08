package com.chat.api.infrastructure.boxes;

import com.chat.api.common.interfaces.PasswordMatches;
import com.chat.api.common.interfaces.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@PasswordMatches
public class RegistrationBox {

    @NotNull
    @NotEmpty
    private String login;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;
}
