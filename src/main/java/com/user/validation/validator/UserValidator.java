package com.user.validation.validator;

import com.user.repo.UserRepository;
import com.user.validation.annotation.ValidateUserExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator implements ConstraintValidator<ValidateUserExists, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean hasError = false;
        if (userRepository.existsByName(value)) {
            hasError = true;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("User Already Exist")
                    .addPropertyNode("userName")
                    .addConstraintViolation();
        }
        return !hasError;
    }
}
