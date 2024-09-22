package com.amalvadkar.ses.account.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CustomHttpStatusEnum {
    ACCOUNT_LOCKED(1000);

    private final int value;

    public int value() {
        return this.value;
    }
}
