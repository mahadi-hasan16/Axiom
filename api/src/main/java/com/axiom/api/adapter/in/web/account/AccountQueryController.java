package com.axiom.api.adapter.in.web.account;

import com.axiom.api.adapter.out.persistence.AccountPersistenceAdapter;
import com.axiom.api.domain.account.model.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
public class AccountQueryController {
    private final AccountPersistenceAdapter accountPersistenceAdapter;
    public AccountQueryController(AccountPersistenceAdapter accountPersistenceAdapter) {
        this.accountPersistenceAdapter = accountPersistenceAdapter;
    }

    @GetMapping
    public List<Account> getAccounts() {
        return accountPersistenceAdapter.loadAccounts();
    }
}
