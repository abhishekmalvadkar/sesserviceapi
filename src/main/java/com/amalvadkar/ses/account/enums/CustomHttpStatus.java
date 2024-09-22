package com.amalvadkar.ses.account.enums;

public enum CustomHttpStatus{
    ACCOUNT_LOCKED(1000);
    private final int value;
    private CustomHttpStatus(int value) {
        this.value = value;
    }
    public int value() {
        return this.value;
    }
}
