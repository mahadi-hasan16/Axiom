package com.axiom.api.application.account.port.out;

import com.axiom.api.domain.account.model.Account;

public interface SaveAccountPort {
    void save(Account account);
}
