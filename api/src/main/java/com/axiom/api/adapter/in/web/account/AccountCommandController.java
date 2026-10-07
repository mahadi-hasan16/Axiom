package com.axiom.api.adapter.in.web.account;

import com.axiom.api.adapter.out.persistence.AccountPersistenceAdapter;
import com.axiom.api.application.account.command.CreateAccountCommand;
import com.axiom.api.domain.account.model.Account;
import com.axiom.api.domain.account.model.AccountId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/accounts")
public class AccountCommandController {
    private final AccountPersistenceAdapter accountPersistenceAdapter;
    public AccountCommandController(AccountPersistenceAdapter accountPersistenceAdapter)
    {
        this.accountPersistenceAdapter = accountPersistenceAdapter;
    }

    @PostMapping
    public void createAccount(@RequestBody CreateAccountCommand createAccountCommand)
    {
        Account account = Account.create(
                AccountId.generate(),
                createAccountCommand.accountNumber(),
                createAccountCommand.accountName(),
                createAccountCommand.toParentAccountId(),
                createAccountCommand.classification(),
                createAccountCommand.accountType(),
                createAccountCommand.toCurrencyCode(),
                createAccountCommand.postable()
        );
        this.accountPersistenceAdapter.save(account);
    }
}
