package com.axiom.api.application.account.port.in;

import com.axiom.api.domain.account.model.AccountClassification;
import com.axiom.api.domain.account.model.AccountId;
import com.axiom.api.domain.account.model.AccountType;
import com.axiom.api.domain.common.CurrencyCode;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public interface GetAccountTreeQuery {

    List<AccountNode> getAccountTree(AccountTreeFilter filter);

    default List<AccountNode> getActiveAccountTree() {
        return getAccountTree(AccountTreeFilter.onlyActive());
    }

    record AccountTreeFilter(
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

    record AccountNode(
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
}