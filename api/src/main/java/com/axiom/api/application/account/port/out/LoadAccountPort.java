package com.axiom.api.application.account.port.out;

import com.axiom.api.domain.account.model.Account;
import com.axiom.api.domain.account.model.AccountId;

import java.util.List;
import java.util.Optional;

public interface LoadAccountPort {

    List<Account> loadAccounts();

    Optional<Account> loadById(AccountId id);

    boolean existsByAccountNumber(String accountNumber);

    Optional<Account> loadByAccountNumber(String accountNumber);
}
