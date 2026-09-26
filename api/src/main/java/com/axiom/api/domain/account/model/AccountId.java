package com.axiom.api.domain.account.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record AccountId(UUID id) implements Serializable, Comparable<AccountId> {
    public AccountId {
        Objects.requireNonNull(id, "Account Id is required. Account Is can't be null.");
    }

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }

    public static AccountId of(UUID id) {
        return new AccountId(id);
    }

    public static AccountId of(String id) {
        Objects.requireNonNull(id, "Account ID is required. Account ID can't be null.");
        try {
            return new AccountId(UUID.fromString(id.strip()));
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Account Id: " + id, e);
        }
    }

    @Override
    public int compareTo(AccountId other) {
        return id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
