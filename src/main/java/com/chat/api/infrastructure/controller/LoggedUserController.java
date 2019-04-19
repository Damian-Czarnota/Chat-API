package com.chat.api.infrastructure.controller;

import com.chat.api.infrastructure.dto.UserDto;
import com.chat.api.infrastructure.service.LoggedUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/me")
public class LoggedUserController {

    private final LoggedUserService loggedUserService;

    public LoggedUserController(LoggedUserService loggedUserService) {
        this.loggedUserService = loggedUserService;
    }

    @GetMapping()
    public ResponseEntity<UserDto> get() {
        return new ResponseEntity<UserDto>(new UserDto(loggedUserService.currentUser()), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<UserDto> addAvatar(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<UserDto>(new UserDto(loggedUserService.addAvatar(file)), HttpStatus.OK);
    }
}
