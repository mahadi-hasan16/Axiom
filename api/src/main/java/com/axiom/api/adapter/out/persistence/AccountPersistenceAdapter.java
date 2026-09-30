package com.axiom.api.adapter.out.persistence;

import com.axiom.api.adapter.out.persistence.jpa.entity.AccountEntity;
import com.axiom.api.adapter.out.persistence.jpa.repository.AccountRepository;
import com.axiom.api.adapter.out.persistence.mapper.AccountMapper;
import com.axiom.api.application.account.port.out.LoadAccountPort;
import com.axiom.api.domain.account.exception.AccountNotFoundException;
import com.axiom.api.domain.account.model.Account;
import com.axiom.api.domain.account.model.AccountId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly=true)
public class AccountPersistenceAdapter implements LoadAccountPort {
    private final AccountRepository accountRepository;
    public AccountPersistenceAdapter(AccountRepository accountRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository cannot be null");
    }

    @Override
    public List<Account>  loadAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountMapper::toAccount)
                .toList();
    }

    @Override
    public Optional<Account> loadById(AccountId id) {
        AccountEntity accountEntity = accountRepository.findById(id.toUUID())
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id.toUUID() + " not found"));
        Account account = AccountMapper.toAccount(accountEntity);
        return Optional.of(account);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return false;
    }

    @Override
    public Optional<Account> loadByAccountNumber(String accountNumber) {
        return Optional.empty();
    }
}
