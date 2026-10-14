import { AccountClassification } from "./account-classification";
import { AccountType } from "./account-type";

// Matches the nested structure Jackson serializes Java records to.
// AccountId(UUID id)      → { id: string }
// CurrencyCode(String value) → { value: string }

interface AccountId {
    id: string; // UUID as string
}

interface CurrencyCode {
    value: string; // ISO-4217 e.g. "USD", "BDT"
}

export interface Account {
    id:             AccountId;
    accountNumber:  string;
    name:           string;
    parentId:       AccountId | null;
    classification: AccountClassification;
    accountType:    AccountType;
    currencyCode:   CurrencyCode;
    postable:       boolean;
    active:         boolean;
    version:        number;
}
