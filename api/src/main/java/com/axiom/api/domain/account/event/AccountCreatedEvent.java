package com.axiom.api.domain.account.event;

import com.axiom.api.domain.account.model.AccountClassification;
import com.axiom.api.domain.account.model.AccountId;
import com.axiom.api.domain.account.model.AccountType;
import com.axiom.api.domain.common.CurrencyCode;
import com.axiom.api.domain.common.DomainEvent;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record AccountCreatedEvent(
        UUID eventId,
        Instant occurredAt,
        AccountId accountId,
        String accountNumber,
        String accountName,
        AccountId parentId,
        AccountClassification classification,
        AccountType accountType,
        CurrencyCode currencyCode,
        boolean postable
) implements DomainEvent {

    public AccountCreatedEvent {
        Objects.requireNonNull(eventId, "Event ID cannot be null");
        Objects.requireNonNull(occurredAt, "Occurred timestamp cannot be null");
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        Objects.requireNonNull(accountNumber, "Account number cannot be null");
        Objects.requireNonNull(accountName, "Account name cannot be null");
        Objects.requireNonNull(classification, "Account classification cannot be null");
        Objects.requireNonNull(accountType, "Account type cannot be null");
        Objects.requireNonNull(currencyCode, "Currency code cannot be null");
    }

    public static AccountCreatedEvent of(
            AccountId accountId,
            String accountCode,
            String accountName,
            AccountId parentId,
            AccountClassification classification,
            AccountType accountType,
            CurrencyCode currencyCode,
            boolean postable
    ) {
        return new AccountCreatedEvent(
                UUID.randomUUID(),
                Instant.now(),
                accountId,
                accountCode,
                accountName,
                parentId,
                classification,
                accountType,
                currencyCode,
                postable
        );
    }

    @Override
    public String aggregateType() {
        return "ACCOUNT";
    }

    @Override
    public String aggregateId() {
        return accountId.toString();
    }
}