package com.axiom.api.application.account.port.out;

import com.axiom.api.domain.account.model.Account;

public interface ExecuteAccountProcedurePort {
    void executeCreate(Account account);

    void executeUpdate(Account account);
}
