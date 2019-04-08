package com.chat.api.infrastructure.controller;

import com.chat.api.infrastructure.boxes.RegistrationBox;
import com.chat.api.infrastructure.service.classes.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationBox registrationBox){
        return userService.register(registrationBox);
    }

}
