package com.axiom.api.domain.account.model;

public enum AccountClassification {
    ASSET(DebitCredit.DEBIT, FinancialStatementType.BALANCE_SHEET),
    EXPENSE(DebitCredit.DEBIT, FinancialStatementType.INCOME_STATEMENT),
    REVENUE(DebitCredit.CREDIT, FinancialStatementType.INCOME_STATEMENT),
    LIABILITY(DebitCredit.CREDIT, FinancialStatementType.BALANCE_SHEET),
    EQUITY(DebitCredit.CREDIT, FinancialStatementType.BALANCE_SHEET);

    private final DebitCredit normalBalance;
    private final FinancialStatementType statementType;

    AccountClassification(DebitCredit normalBalance, FinancialStatementType statementType) {
        this.normalBalance = normalBalance;
        this.statementType = statementType;
    }

    public DebitCredit getNormalBalance() {
        return normalBalance;
    }

    public FinancialStatementType getStatementType() {
        return statementType;
    }

    public boolean isDebitNormal() {
        return this.normalBalance == DebitCredit.DEBIT;
    }

    public boolean isCreditNormal() {
        return this.normalBalance == DebitCredit.CREDIT;
    }

    public boolean isBalanceSheetAccount() {
        return this.statementType == FinancialStatementType.BALANCE_SHEET;
    }

    public boolean isIncomeStatementAccount() {
        return this.statementType == FinancialStatementType.INCOME_STATEMENT;
    }
}
