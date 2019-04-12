package com.chat.api.common.classes;

import com.chat.api.common.interfaces.PasswordMatches;
import com.chat.api.infrastructure.boxes.messages.RegistrationBox;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistrationBox user = (RegistrationBox) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
