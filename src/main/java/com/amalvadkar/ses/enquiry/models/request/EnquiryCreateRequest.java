package com.amalvadkar.ses.enquiry.models.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record EnquiryCreateRequest(

        @NotEmpty(message = "name is required")
        String name,

        @NotEmpty(message = "contact number is required")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "invalid phone number")
        String contactNumber,

        @NotEmpty(message = "class mode is required")
        String classMode,

        @NotEmpty(message = "courses should not be null or empty")
        Long[] courseIds

) {
}
