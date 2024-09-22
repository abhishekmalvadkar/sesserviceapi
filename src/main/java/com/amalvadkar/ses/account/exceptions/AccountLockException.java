package com.amalvadkar.ses.account.exceptions;

import com.amalvadkar.ses.account.enums.CustomHttpStatusEnum;
import com.amalvadkar.ses.common.exceptions.SesException;

public class AccountLockException extends SesException {
    public AccountLockException(String message) {
        super(message, CustomHttpStatusEnum.ACCOUNT_LOCKED.value());
    }
}
