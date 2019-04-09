package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.boxes.LoginBox;
import com.chat.api.infrastructure.boxes.RegistrationBox;
import com.chat.api.infrastructure.dao.UserRepository;
import com.chat.api.infrastructure.enums.RoleName;
import com.chat.api.infrastructure.model.Role;
import com.chat.api.infrastructure.model.User;
import com.chat.api.infrastructure.service.interfaces.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public ResponseEntity<?> register(RegistrationBox registrationBox) {

        if(userRepository.existsByLogin(registrationBox.getLogin()))
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(registrationBox.getEmail()))
            return new ResponseEntity<>("Email is in use", HttpStatus.BAD_REQUEST);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(RoleName.ROLE_USER));

        User user = User.builder().login(registrationBox.getLogin())
                .email(registrationBox.getEmail())
                .name(registrationBox.getName())
                .password(passwordEncoder.encode(registrationBox.getPassword()))
                .roles(roleSet)
                .build();

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(LoginBox loginBox) {
        User user = userRepository.findByLogin(loginBox.getLogin())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        if(passwordEncoder.matches(loginBox.getPassword(), user.getPassword()))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }
}
