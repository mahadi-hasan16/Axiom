package com.axiom.api.domain.common;

import java.util.Objects;

public class Currency {

    public Currency(
            CurrencyCode code,
            int numericCode,
            int fractionDigits,
            String symbol,
            String name,
            boolean baseCurrency,
            boolean active
    ) {
        this.code = Objects.requireNonNull(code, "Currency code cannot be null");
        this.numericCode = validateNumericCode(numericCode);
        this.fractionDigits = validateFractionDigits(fractionDigits);
        this.symbol = Objects.requireNonNull(symbol, "Symbol cannot be null").strip();
        this.name = Objects.requireNonNull(name, "Currency name cannot be null").strip();
        this.baseCurrency = baseCurrency;
        this.active = active;
    }

    private final CurrencyCode code;
    private final int numericCode;
    private final int fractionDigits;
    private final String symbol;
    private final String name;
    private final boolean baseCurrency;
    private boolean active;

    public CurrencyCode getCode() { return code; }
    public int getNumericCode() { return numericCode; }
    public int getFractionDigits() { return fractionDigits; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public boolean isBaseCurrency() { return baseCurrency; }
    public boolean isActive() { return active; }


    private int validateNumericCode(int numericCode) {
        if(numericCode < 0 || numericCode > 999) {
            throw new IllegalArgumentException("Invalid numeric code: " + numericCode + ". Numeric code must be between 0001 and 999");
        }
        return numericCode;
    }

    private int validateFractionDigits(int digits) {
        if (digits < 0 || digits > 6) {
            throw new IllegalArgumentException("Fraction digits must be between 0 and 6. Provided: " + digits);
        }
        return digits;
    }

    public void deactivate() {
        if(this.baseCurrency) {
            throw new IllegalStateException("Cannot deactivate the system base reporting currency");
        }
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Currency that)) return false;
        if(this == object) return true;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
