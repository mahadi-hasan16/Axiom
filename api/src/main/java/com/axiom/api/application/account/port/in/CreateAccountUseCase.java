package com.axiom.api.application.account.port.in;

import com.axiom.api.domain.account.model.AccountClassification;
import com.axiom.api.domain.account.model.AccountId;
import com.axiom.api.domain.account.model.AccountType;
import com.axiom.api.domain.common.CurrencyCode;

import java.util.Objects;
import java.util.UUID;

public interface CreateAccountUseCase {
    AccountId createAccount(CreateAccountCommand command);

    record CreateAccountCommand(
            String accountNumber,
            String accountName,
            UUID parentId,
            AccountClassification classification,
            AccountType accountType,
            String currencyCode,
            boolean postable
    ) {
        public CreateAccountCommand {
            Objects.requireNonNull(accountNumber, "Account number is required");
            Objects.requireNonNull(accountName, "Account name is required");
            Objects.requireNonNull(classification, "Account classification is required");
            Objects.requireNonNull(accountType, "Account type is required");
            Objects.requireNonNull(currencyCode, "Currency code is required");

            accountNumber = accountNumber.strip();
            accountName = accountName.strip();
            currencyCode = currencyCode.strip().toUpperCase();
        }

        public CurrencyCode toCurrencyCode() {
            return CurrencyCode.of(currencyCode);
        }

        public AccountId toParentAccountId() {
            return parentId != null ? AccountId.of(parentId) : null;
        }
    }
}