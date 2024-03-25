package com.user.model.request;

import com.user.validation.annotation.ValidateSignupDetails;
import com.user.validation.annotation.ValidateUserExists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ValidateSignupDetails
public class SignUpDto {

    @ValidateUserExists
    private String userName;

    private String email;

    private String password;

    private String confirmPassword;

    private String roles;
}
