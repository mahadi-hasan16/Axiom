package com.axiom.api.domain.account.exception;

import com.axiom.api.domain.common.DomainException;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException(String message) {
        super("ACCOUNT_NOT_FOUND", message);
    }
}
