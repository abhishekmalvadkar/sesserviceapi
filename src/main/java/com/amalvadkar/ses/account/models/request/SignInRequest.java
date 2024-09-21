package com.amalvadkar.ses.account.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignInRequest {

    @Email(message="Invalid Email")
    @NotEmpty(message="Missing Email")
    private final String email;

    @NotEmpty(message="Missing Password")
    private final String password;
}
