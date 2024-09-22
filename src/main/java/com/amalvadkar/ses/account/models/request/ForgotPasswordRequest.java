package com.amalvadkar.ses.account.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;



public record ForgotPasswordRequest(
        @Email(message = "Invalid email")
        @NotEmpty(message = "email is required ")
        String email) {

}
