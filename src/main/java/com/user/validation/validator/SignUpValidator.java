package com.user.validation.validator;

import com.user.model.request.SignUpDto;
import com.user.validation.annotation.ValidateSignupDetails;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class SignUpValidator implements ConstraintValidator<ValidateSignupDetails, SignUpDto> {

    @Override
    public void initialize(ValidateSignupDetails constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SignUpDto request, ConstraintValidatorContext context) {
        boolean hasError = false;
        if (StringUtils.isBlank(request.getEmail())) {
            hasError = true;
            addConstraintViolation(context, "email", "Email is a Required Attribute");
        } else if (!request.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            hasError = true;
            addConstraintViolation(context, "email", "Enter a Valid Email");
        }

        if (StringUtils.isNoneBlank(request.getPassword(), request.getConfirmPassword()) && (!request.getPassword().equals(request.getConfirmPassword()))) {
            hasError = true;
            addConstraintViolation(context, "passowrd", "Password doesn't match!!");

        }

        return !hasError;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String propertyPath, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(propertyPath)
                .addConstraintViolation();
    }
}
