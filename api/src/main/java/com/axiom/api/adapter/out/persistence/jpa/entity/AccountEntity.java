package com.axiom.api.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "account_number", nullable = false, unique = true, length = 32)
    private String accountNumber;

    @Column(name = "account_name", nullable = false, length = 128)
    private String accountName;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "classification", nullable = false, length = 32)
    private String classification;

    @Column(name = "account_type", nullable = false, length = 32)
    private String accountType;

    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "is_postable", nullable = false)
    private boolean postable;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "hierarchy_level", nullable = false)
    private int hierarchyLevel;

    @Column(name = "tree_path", nullable = false)
    private String treePath;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected AccountEntity() {
    }

    public AccountEntity(
            UUID id,
            String accountNumber,
            String accountName,
            UUID parentId,
            String classification,
            String accountType,
            String currencyCode,
            boolean postable,
            boolean active,
            int hierarchyLevel,
            String treePath
    ) {
        this.id = Objects.requireNonNull(id, "Account ID cannot be null");
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.parentId = parentId;
        this.classification = classification;
        this.accountType = accountType;
        this.currencyCode = currencyCode;
        this.postable = postable;
        this.active = active;
        this.hierarchyLevel = hierarchyLevel;
        this.treePath = treePath;
    }

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public UUID getParentId() {
        return parentId;
    }

    public String getClassification() {
        return classification;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public boolean isPostable() {
        return postable;
    }

    public boolean isActive() {
        return active;
    }

    public int getHierarchyLevel() {
        return hierarchyLevel;
    }

    public String getTreePath() {
        return treePath;
    }

    public Long getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}