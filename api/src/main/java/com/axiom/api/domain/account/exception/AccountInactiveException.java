package com.axiom.api.domain.account.exception;

import com.axiom.api.domain.common.DomainException;

public class AccountInactiveException extends DomainException {
    public AccountInactiveException(String message) {
        super("INACTIVE_ACCOUNT", message);
    }
}
