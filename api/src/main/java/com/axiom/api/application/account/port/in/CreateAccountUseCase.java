package com.axiom.api.application.account.port.in;

import com.axiom.api.application.account.command.CreateAccountCommand;
import com.axiom.api.domain.account.model.AccountId;

public interface CreateAccountUseCase {
    AccountId createAccount(CreateAccountCommand command);
}