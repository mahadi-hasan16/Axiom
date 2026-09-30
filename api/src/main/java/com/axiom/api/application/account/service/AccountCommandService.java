package com.axiom.api.application.account.service;

import com.axiom.api.application.account.command.CreateAccountCommand;
import com.axiom.api.application.account.port.in.CreateAccountUseCase;
import com.axiom.api.application.account.port.out.ExecuteAccountProcedurePort;
import com.axiom.api.application.account.port.out.LoadAccountPort;
import com.axiom.api.domain.account.exception.DuplicateAccountNumberException;
import com.axiom.api.domain.account.exception.InvalidAccountHierarchyException;
import com.axiom.api.domain.account.exception.ParentAccountNotFoundException;
import com.axiom.api.domain.account.model.Account;
import com.axiom.api.domain.account.model.AccountId;

import java.util.Objects;

public class AccountCommandService implements CreateAccountUseCase {
    private final LoadAccountPort loadAccountPort;
    private final ExecuteAccountProcedurePort executeAccountProcedurePort;

    public AccountCommandService(LoadAccountPort loadAccountPort, ExecuteAccountProcedurePort executeAccountProcedurePort) {
        this.loadAccountPort = loadAccountPort;
        this.executeAccountProcedurePort = executeAccountProcedurePort;
    }
    @Override
    public AccountId createAccount(CreateAccountCommand createAccountCommand) {
        Objects.requireNonNull(createAccountCommand, "CreateAccountCommand cannot be null");

        if(loadAccountPort.existsByAccountNumber(createAccountCommand.accountNumber())) {
            throw new DuplicateAccountNumberException("Account number already exists. Account Number: " + createAccountCommand.accountNumber() );
        }

        AccountId parentAccountId = createAccountCommand.toParentAccountId();

        if(parentAccountId != null) {
            Account parentAccount = loadAccountPort.loadById(parentAccountId)
                    .orElseThrow(() -> new ParentAccountNotFoundException("Parent Account not found: " + parentAccountId));

            if(parentAccount.getClassification() != createAccountCommand.classification()) {
                throw new InvalidAccountHierarchyException(
                        "Child classification (" + createAccountCommand.classification() + ") must match parent classification (" + parentAccount.getClassification() + ")"
                );
            }
        }

        AccountId id = AccountId.generate();
        Account account = Account.create(
                id,
                createAccountCommand.accountNumber(),
                createAccountCommand.accountName(),
                parentAccountId,
                createAccountCommand.classification(),
                createAccountCommand.accountType(),
                createAccountCommand.toCurrencyCode(),
                createAccountCommand.postable()
        );

        executeAccountProcedurePort.executeCreate(account);

        return account.getId();
    }
}
