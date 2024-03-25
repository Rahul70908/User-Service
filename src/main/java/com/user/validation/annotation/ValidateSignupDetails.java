package com.user.validation.annotation;

import com.user.validation.validator.SignUpValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = SignUpValidator.class)
public @interface ValidateSignupDetails {

    String message() default "Invalid Details";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
