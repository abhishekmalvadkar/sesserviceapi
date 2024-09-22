package com.amalvadkar.ses.account.exceptions;

import com.amalvadkar.ses.account.enums.CustomHttpStatus;
import com.amalvadkar.ses.common.exceptions.SesException;

public class AccountLockException extends SesException {
    public AccountLockException(String message) {
        super(message, CustomHttpStatus.ACCOUNT_LOCKED.value());
    }
}
