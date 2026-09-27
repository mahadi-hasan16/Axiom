package com.axiom.api.domain.account.exception;

import com.axiom.api.domain.common.DomainException;

public class ParentAccountNotFoundException extends DomainException {
    public ParentAccountNotFoundException(String message) {
        super("PARENT_ACCOUNT_NOT_FOUND", message);
    }
}
