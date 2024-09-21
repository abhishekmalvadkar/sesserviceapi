package com.amalvadkar.ses.account.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record SignUpRequest(
        @NotEmpty(message = "name is required")
        String name,
        @Email(message = "invalid email")
        @NotEmpty(message = "email is required")
        String email,
        @Pattern(regexp = "(^$|[0-9]{10})", message = "invalid phone number")
        String phone) {
}
