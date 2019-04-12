package com.chat.api.infrastructure.service.interfaces;

import com.chat.api.infrastructure.boxes.messages.LoginBox;
import com.chat.api.infrastructure.boxes.messages.RegistrationBox;
import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity<?> register(RegistrationBox registrationBox);

    ResponseEntity<?> login(LoginBox loginBox);
}

