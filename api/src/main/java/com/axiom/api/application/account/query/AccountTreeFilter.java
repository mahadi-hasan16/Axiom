package com.axiom.api.application.account.query;

import com.axiom.api.domain.account.model.AccountClassification;

public record AccountTreeFilter(
        boolean includeInactive,
        AccountClassification classification
) {
    public static AccountTreeFilter all() {
        return new AccountTreeFilter(true, null);
    }

    public static AccountTreeFilter onlyActive() {
        return new AccountTreeFilter(false, null);
    }

    public static AccountTreeFilter forClassification(AccountClassification classification) {
        return new AccountTreeFilter(false, classification);
    }
}