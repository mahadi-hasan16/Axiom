package com.axiom.api.adapter.out.persistence.mapper;

import com.axiom.api.adapter.out.persistence.jpa.entity.AccountEntity;
import com.axiom.api.domain.account.model.Account;
import com.axiom.api.domain.account.model.AccountClassification;
import com.axiom.api.domain.account.model.AccountId;
import com.axiom.api.domain.account.model.AccountType;
import com.axiom.api.domain.common.CurrencyCode;

public class AccountMapper {
    public static Account toAccount(AccountEntity accountEntity) {
        if (accountEntity == null) {
            return null;
        }

        return new Account(
                new AccountId(accountEntity.getId()),
                accountEntity.getAccountNumber(),
                accountEntity.getAccountName(),
                accountEntity.getParentId() != null ? new AccountId(accountEntity.getParentId()) : null,
                AccountClassification.valueOf(accountEntity.getClassification()),
                AccountType.valueOf(accountEntity.getAccountType()),
                CurrencyCode.of(accountEntity.getCurrencyCode()),
                accountEntity.isPostable(),
                accountEntity.isActive(),
                accountEntity.getVersion() != null ? accountEntity.getVersion() : 0L
        );
    }

    public static AccountEntity toAccountEntity(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountEntity(
                account.getId().toUUID(),
                account.getAccountNumber(),
                account.getName(),
                account.getParentId() != null ? account.getParentId().toUUID() : null,
                account.getClassification().name(),
                account.getAccountType().name(),
                account.getCurrencyCode().value(),
                account.isPostable(),
                account.isActive(),
                0,
                ""
        );
    }
}
