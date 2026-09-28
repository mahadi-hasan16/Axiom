package com.axiom.api.application.account.port.in;

import com.axiom.api.application.account.query.AccountTreeFilter;
import com.axiom.api.domain.account.model.AccountNode;

import java.util.List;

public interface GetAccountTreeQuery {

    List<AccountNode> getAccountTree(AccountTreeFilter filter);

    default List<AccountNode> getActiveAccountTree() {
        return getAccountTree(AccountTreeFilter.onlyActive());
    }
}