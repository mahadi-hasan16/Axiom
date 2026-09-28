package com.axiom.api.domain.account.model;

import com.axiom.api.domain.common.CurrencyCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record AccountNode(
        AccountId id,
        String accountNumber,
        String accountName,
        AccountId parentId,
        AccountClassification classification,
        AccountType accountType,
        CurrencyCode currencyCode,
        boolean postable,
        boolean active,
        List<AccountNode> children
) {
    public AccountNode {
        Objects.requireNonNull(id, "Account ID cannot be null");
        Objects.requireNonNull(accountNumber, "Account number cannot be null");
        Objects.requireNonNull(accountName, "Account name cannot be null");
        Objects.requireNonNull(classification, "Classification cannot be null");
        Objects.requireNonNull(accountType, "Account type cannot be null");
        Objects.requireNonNull(currencyCode, "Currency code cannot be null");

        children = children != null ? List.copyOf(children) : Collections.emptyList();
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }
}