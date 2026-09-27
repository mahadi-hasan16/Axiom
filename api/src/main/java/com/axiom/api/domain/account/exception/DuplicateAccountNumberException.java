package com.axiom.api.domain.account.exception;

import com.axiom.api.domain.common.DomainException;

public class DuplicateAccountNumberException extends DomainException {
    public DuplicateAccountNumberException(String message) {
        super("DUPLICATE_ACCOUNT_NUMBER", message);
    }
}
