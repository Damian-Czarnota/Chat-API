package com.chat.api.infrastructure.service;

import com.chat.api.infrastructure.dao.UserRepository;
import com.chat.api.infrastructure.exceptions.FileStorageException;
import com.chat.api.infrastructure.model.User;
import com.chat.api.security.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LoggedUserService {

    private final UserRepository userRepository;

    public LoggedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            throw new FileStorageException("Could not store file " + file.getName() + ". Please try again!", e);
        }
        return user;
    }
}
