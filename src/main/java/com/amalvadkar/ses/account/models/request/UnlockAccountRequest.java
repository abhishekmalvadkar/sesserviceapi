package com.amalvadkar.ses.account.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UnlockAccountRequest(

        @Email(message = "email is not valid")
        @NotEmpty(message = "email is required ")
        String email,

        @NotEmpty(message = "temp password is required")
        String tempPassword,

        @NotEmpty(message = "new password is required")
        String newPassword,

        @NotEmpty(message = "confirm  password is required")
        String confirmPassword) {

}
