package com.amalvadkar.ses.enquiry.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnquiryStatusEnum {
    NEW("new"),
    ENROLLED("enrolled"),
    LOST("lost");

    private final String status;
    public String value() {
        return this.status;
    }

}
