package com.amalvadkar.ses.enquiry.models.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnquiryUpdateRequest(

        @NotNull(message = "id is required")
        Long id,


        @NotEmpty(message = "name is required")
        String name,

        @NotEmpty(message = "contact number is required")
        @Pattern(regexp = "(^$|[0-9]{10})", message = "invalid phone number")
        String contactNumber,

        @NotEmpty(message = "class mode number is required")
        String classMode,

        @NotEmpty(message="enquiryStatus is required")
        String enquiryStatus,

        @NotNull(message = "courses should not be null")
        Long[] courseIds
) {
}
