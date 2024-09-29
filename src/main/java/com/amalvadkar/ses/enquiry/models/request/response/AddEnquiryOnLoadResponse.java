package com.amalvadkar.ses.enquiry.models.request.response;

import java.util.Map;

public record AddEnquiryOnLoadResponse(
        Map<String, Object> meteData
) {
}
