package com.axiom.api.domain.account.model;

import com.axiom.api.domain.account.event.AccountCreatedEvent;
import com.axiom.api.domain.common.CurrencyCode;
import com.axiom.api.domain.common.DomainEvent;
import com.axiom.api.domain.common.DomainException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Account implements Serializable {

    private final AccountId id;
    private final String accountNumber;
    private String name;
    private AccountId parentId;
    private final AccountClassification classification;
    private final AccountType accountType;
    private final CurrencyCode currencyCode;
    private boolean isPostable;
    private boolean active;
    private final long version;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Account(
            AccountId id,
            String accountNumber,
            String name,
            AccountId parentId,
            AccountClassification classification,
            AccountType accountType,
            CurrencyCode currencyCode,
            boolean isPostable,
            boolean active,
            long version
    ) {
        this.id = Objects.requireNonNull(id, "Account ID cannot be null");
        this.accountNumber = validateAccountCode(accountNumber);
        this.name = validateName(name);
        this.parentId = parentId;
        this.classification = Objects.requireNonNull(classification, "Account classification cannot be null");
        this.accountType = Objects.requireNonNull(accountType, "Account type cannot be null");
        this.currencyCode = Objects.requireNonNull(currencyCode, "Currency code cannot be null");
        this.isPostable = isPostable;
        this.active = active;
        this.version = version;

        validateClassificationCompatibility(this.accountType, this.classification);
        validateParentRelationship(this.id, this.parentId);
    }

    public static Account create(
            AccountId id,
            String accountNumber,
            String name,
            AccountId parentId,
            AccountClassification classification,
            AccountType accountType,
            CurrencyCode currencyCode,
            boolean isHeader
    ) {
        Account account = new Account(
                id,
                accountNumber,
                name,
                parentId,
                classification,
                accountType,
                currencyCode,
                isHeader,
                true,
                0L
        );

        account.registerEvent(AccountCreatedEvent.of(
                id,
                accountNumber,
                name,
                parentId,
                classification,
                accountType,
                currencyCode,
                isHeader
        ));

        return account;
    }

    public void validateCanAcceptPosting() {
        if (!this.active) {
            throw new AccountPostingException("Cannot post transactions to inactive account: " + this.accountNumber);
        }
        if (this.isPostable) {
            throw new AccountPostingException("Cannot post transactions to header/summary account: " + this.accountNumber);
        }
    }

    public void assignParent(AccountId newParentId) {
        validateParentRelationship(this.id, newParentId);
        this.parentId = newParentId;
    }

    public void updateDetails(String newName) {
        this.name = validateName(newName);
    }

    public void markAsPostable() {
        this.isPostable = true;
    }

    public void markAsNonpostable() {
        this.isPostable = false;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    private void validateClassificationCompatibility(AccountType type, AccountClassification classification) {
        if (!type.isCompatibleWith(classification)) {
            throw new IllegalArgumentException(
                    "AccountType " + type + " is not permissible under classification " + classification
            );
        }
    }

    private String validateAccountCode(String code) {
        Objects.requireNonNull(code, "Account code cannot be null");
        String trimmed = code.strip();
        if (trimmed.isEmpty() || trimmed.length() > 50) {
            throw new IllegalArgumentException("Account code must be between 1 and 50 characters");
        }
        return trimmed;
    }

    private String validateName(String name) {
        Objects.requireNonNull(name, "Account name cannot be null");
        String trimmed = name.strip();
        if (trimmed.isEmpty() || trimmed.length() > 200) {
            throw new IllegalArgumentException("Account name must be between 1 and 200 characters");
        }
        return trimmed;
    }

    private void validateParentRelationship(AccountId currentId, AccountId targetParentId) {
        if (targetParentId != null && targetParentId.equals(currentId)) {
            throw new IllegalArgumentException("An account cannot be its own parent. AccountId: " + currentId);
        }
    }

    protected void registerEvent(DomainEvent event) {
        this.domainEvents.add(Objects.requireNonNull(event, "Domain event cannot be null"));
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = Collections.unmodifiableList(new ArrayList<>(this.domainEvents));
        this.domainEvents.clear();
        return events;
    }

    public AccountId getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public AccountId getParentId() { return parentId; }
    public AccountClassification getClassification() { return classification; }
    public AccountType getAccountType() { return accountType; }
    public CurrencyCode getCurrencyCode() { return currencyCode; }
    public boolean isPostable() { return isPostable; }
    public boolean isActive() { return active; }
    public long getVersion() { return version; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class AccountPostingException extends DomainException {
        public AccountPostingException(String message) {
            super("ACCOUNT_POSTING_PROHIBITED", message);
        }
    }
}