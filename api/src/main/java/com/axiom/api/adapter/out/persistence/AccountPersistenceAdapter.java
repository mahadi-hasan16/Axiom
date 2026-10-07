package com.axiom.api.adapter.out.persistence;

import com.axiom.api.adapter.out.persistence.jpa.repository.AccountRepository;
import com.axiom.api.adapter.out.persistence.jpa.mapper.AccountMapper;
import com.axiom.api.application.account.port.out.LoadAccountPort;
import com.axiom.api.application.account.port.out.SaveAccountPort;
import com.axiom.api.domain.account.model.Account;
import com.axiom.api.domain.account.model.AccountId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Transactional(readOnly=true)
public class AccountPersistenceAdapter implements LoadAccountPort, SaveAccountPort {
    private final AccountRepository accountRepository;
    public AccountPersistenceAdapter(AccountRepository accountRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository, "accountRepository cannot be null");
    }

    @Override
    @Transactional
    public void save(Account account) {
        accountRepository.save(AccountMapper.toAccountEntity(account));
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
        return accountRepository.findById(id.toUUID())
                .map(AccountMapper::toAccount);
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber);
    }

    @Override
    public Optional<Account> loadByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(AccountMapper::toAccount);
    }
}
