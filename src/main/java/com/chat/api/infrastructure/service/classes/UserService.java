package com.chat.api.infrastructure.service.classes;

import com.chat.api.infrastructure.boxes.messages.LoginBox;
import com.chat.api.infrastructure.boxes.messages.RegistrationBox;
import com.chat.api.infrastructure.boxes.responses.AuthenticationResponse;
import com.chat.api.infrastructure.dao.UserRepository;
import com.chat.api.infrastructure.enums.RoleName;
import com.chat.api.infrastructure.exceptions.CustomError;
import com.chat.api.infrastructure.model.Role;
import com.chat.api.infrastructure.model.User;
import com.chat.api.infrastructure.service.interfaces.IUserService;
import com.chat.api.security.JWT.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final EmailServiceImpl emailService;
    private SimpleMailMessage template;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, EmailServiceImpl emailService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.emailService = emailService;
    }

    @Override
    public ResponseEntity<?> register(RegistrationBox registrationBox) {

        if(userRepository.existsByUsername(registrationBox.getUsername()))
            return new ResponseEntity<>(new CustomError(2, "Username is taken"), HttpStatus.BAD_REQUEST);

        if(userRepository.existsByEmail(registrationBox.getEmail()))
            return new ResponseEntity<>(new CustomError(3, "Email is in use"), HttpStatus.BAD_REQUEST);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role(RoleName.ROLE_USER));

        User user = User.builder().username(registrationBox.getUsername())
                .email(registrationBox.getEmail())
                .name(registrationBox.getUsername())
                .password(passwordEncoder.encode(registrationBox.getPassword()))
                .roles(roleSet)
                .build();

        userRepository.save(user);

        Context context = new Context();
        context.setVariable("header", "Thank you for registration!");
        context.setVariable("title", "Thank you for registration!");
        context.setVariable("description", "Thank you for your registration! :)");

        emailService.sendEmail(user.getEmail(),
                "Thank you for registration!",
                context
                );
        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login(LoginBox loginBox) {
        Authentication authentication;

        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginBox.getUsername(), loginBox.getPassword()));
        }
        catch(AuthenticationException e){
            return new ResponseEntity(
                    new CustomError(1, "Bad credentials"), null, HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getAuthorities()));
    }
}
