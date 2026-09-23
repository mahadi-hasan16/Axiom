package com.axiom.api.domain.account.model;

import java.util.EnumSet;
import java.util.Set;

public enum AccountType {
    CASH(Set.of(AccountClassification.ASSET)),
    BANK(Set.of(AccountClassification.ASSET)),
    RECEIVABLE(Set.of(AccountClassification.ASSET)),
    PAYABLE(Set.of(AccountClassification.LIABILITY)),
    STANDARD(EnumSet.allOf(AccountClassification.class));

    private final Set<AccountClassification> accountClassifications;

    AccountType(Set<AccountClassification> accountClassifications) {
        this.accountClassifications = accountClassifications;
    }

    public boolean isCompatibleWith(AccountClassification accountClassification) {
        return this.accountClassifications.contains(accountClassification);
    }

    public boolean isLiquidityAccount(){
        return this == CASH || this == BANK;
    }

    public boolean requiresSubledger() {
        return this == RECEIVABLE || this == PAYABLE;
    }
}
