package com.amalvadkar.ses;

import com.amalvadkar.ses.common.models.response.KeyValueResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "ses")
public record ApplicationProperties(

        List<KeyValueResponse<String,String>> enquiryStatusDropdownOptions,
        List<KeyValueResponse<String,String>> classModeDropDownOptions
) {
}
