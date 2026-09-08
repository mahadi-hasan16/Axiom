package com.axiom.api.domain.common;

import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount,
                    CurrencyCode currencyCode,
                    int fractionDigits) implements Serializable, Comparable<Money> {
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    public Money{
        Objects.requireNonNull(amount, "Amount must not be null");
        Objects.requireNonNull(currencyCode, "CurrencyCode must not be null");
        if(fractionDigits < 0 || fractionDigits > 6) {
            throw new IllegalArgumentException("Fraction digits must be between 0 and 6");
        }
        amount = amount.setScale(fractionDigits, DEFAULT_ROUNDING_MODE);
    }

    public static Money of(BigDecimal amount, Currency currency){
        Objects.requireNonNull(currency, "Currency must not be null");

        if(!currency.isActive()) {
            throw new IllegalArgumentException("Currency code must be active");
        }
        return  new Money(amount, currency.getCode(), currency.getFractionDigits());
    }

    public static Money of(String amount, Currency currency){
        return  of(new BigDecimal(amount), currency);
    }

    public static Money of(long amount, Currency currency){
        return  of(BigDecimal.valueOf(amount), currency);
    }

    public static Money zero(Currency currency) {
        return of(BigDecimal.ZERO, currency);
    }

    public Money plus(Money other){
        assertSameCurrencyAndScale(other);
        return new Money(this.amount.add(other.amount), this.currencyCode, this.fractionDigits);
    }

    public Money minus(Money other) {
        assertSameCurrencyAndScale(other);
        return new Money(this.amount.subtract(other.amount), this.currencyCode, this.fractionDigits);
    }

    public Money times(BigDecimal multiplier) {
        Objects.requireNonNull(multiplier, "Multiplier cannot be null");
        return new Money(this.amount.multiply(multiplier), this.currencyCode, this.fractionDigits);
    }

    public Money times(long multiplier) {
        return times(BigDecimal.valueOf(multiplier));
    }

    public Money negate() {
        return new Money(this.amount.negate(), this.currencyCode, this.fractionDigits);
    }

    public Money abs() {
        return new Money(this.amount.abs(), this.currencyCode, this.fractionDigits);
    }

    public boolean isZero() {
        return this.amount.signum() == 0;
    }

    public boolean isPositive() {
        return this.amount.signum() > 0;
    }

    public boolean isNegative() {
        return this.amount.signum() < 0;
    }

    public boolean isGreaterThan(Money other) {
        return compareTo(other) > 0;
    }

    public boolean isLessThan(Money other) {
        return compareTo(other) < 0;
    }

    public Money[] allocate(int targets) {
        if (targets <= 0) {
            throw new IllegalArgumentException("Allocation targets must be strictly greater than zero");
        }

        BigDecimal totalMinorUnits = this.amount.movePointRight(this.fractionDigits);
        BigDecimal[] divAndRem = totalMinorUnits.divideAndRemainder(BigDecimal.valueOf(targets));

        long baseMinorUnits = divAndRem[0].longValue();
        long remainder = divAndRem[1].longValue();

        Money[] allocations = new Money[targets];
        for (int i = 0; i < targets; i++) {
            long unitValue = baseMinorUnits + (i < remainder ? 1 : 0);
            allocations[i] = new Money(
                    BigDecimal.valueOf(unitValue).movePointLeft(this.fractionDigits),
                    this.currencyCode,
                    this.fractionDigits
            );
        }
        return allocations;
    }

    private void assertSameCurrencyAndScale(Money other) {
        Objects.requireNonNull(other, "Money operand cannot be null");
        if (!this.currencyCode.equals(other.currencyCode)) {
            throw new IllegalArgumentException(
                    "Currency mismatch: Cannot operate between " + this.currencyCode + " and " + other.currencyCode
            );
        }
        if (this.fractionDigits != other.fractionDigits) {
            throw new IllegalStateException(
                    "Scale mismatch: " + this.fractionDigits + " vs " + other.fractionDigits
            );
        }
    }

    @Override
    public int compareTo(@NonNull Money other) {
        assertSameCurrencyAndScale(other);
        return this.amount.compareTo(other.amount);
    }

    @Override
    @NonNull
    public String toString() {
        return currencyCode.value() + " " + amount.toPlainString();
    }

}
