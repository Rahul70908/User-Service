package com.user.validation.annotation;

import com.user.validation.validator.UserValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UserValidator.class)
public @interface ValidateUserExists {

    String message() default "User already Exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
