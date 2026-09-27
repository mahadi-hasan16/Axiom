package com.axiom.api.domain.account.exception;

import com.axiom.api.domain.common.DomainException;

public class InvalidAccountHierarchyException extends DomainException {
    public InvalidAccountHierarchyException(String message) {
        super("INVALID_ACCOUNT_HIERARCHY", message);
    }
}
