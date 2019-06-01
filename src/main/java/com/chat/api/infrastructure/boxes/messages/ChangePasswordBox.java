package com.chat.api.infrastructure.boxes.messages;

import com.chat.api.common.interfaces.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@PasswordMatches
public class ChangePasswordBox {

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String newPassword;
    private String matchingPassword;
}
