package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.boxes.RegistrationBox;
import com.chat.api.infrastructure.dao.UserRepository;
import com.chat.api.infrastructure.model.User;
import com.chat.api.infrastructure.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> register(RegistrationBox registrationBox) {

        if(userRepository.existsByLogin(registrationBox.getLogin()))
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(registrationBox.getEmail()))
            return new ResponseEntity<>("Email is in use", HttpStatus.BAD_REQUEST);


        User user = User.builder().login(registrationBox.getLogin())
                .email(registrationBox.getEmail())
                .name(registrationBox.getName())
                .password(registrationBox.getPassword())
                .build();

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
}
