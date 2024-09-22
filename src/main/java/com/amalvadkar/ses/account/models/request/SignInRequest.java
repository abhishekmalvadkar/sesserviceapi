package com.amalvadkar.ses.account.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;


public record SignInRequest(
        @Email(message = "Invalid Email")
        @NotEmpty(message = "Missing Email")
        String email,
        @NotEmpty(message = "Missing Password")
        String password) {

}
