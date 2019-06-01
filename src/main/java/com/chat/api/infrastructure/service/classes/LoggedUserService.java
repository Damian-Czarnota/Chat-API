package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.boxes.messages.ChangePasswordBox;
import com.chat.api.infrastructure.dao.UserRepository;
import com.chat.api.infrastructure.exceptions.CustomError;
import com.chat.api.infrastructure.exceptions.DefaultException;
import com.chat.api.infrastructure.model.User;
import com.chat.api.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LoggedUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoggedUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User currentUser() {
        return SecurityUtils.getCurrentUser();
    }
    @Transactional
    public User addAvatar(MultipartFile file){
        User user = SecurityUtils.getCurrentUser();
        try {
            byte[] bytes = file.getBytes();
            user.setProfileImage(bytes);
            userRepository.save(user);
        } catch (IOException e){
            throw new DefaultException("Could not store file " + file.getName() + ". Please try again!", e);
        }
        return user;
    }

    public ResponseEntity<?> changePassword(ChangePasswordBox form) {
        User user = SecurityUtils.getCurrentUser();
        if(!passwordEncoder.matches(form.getPassword(), user.getPassword()))
            return new ResponseEntity<>(new CustomError(4, "Invalid password"), HttpStatus.BAD_REQUEST);

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
    }
}
