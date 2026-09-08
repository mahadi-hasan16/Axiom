package com.axiom.api.domain.common;

import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Objects;

public record ExchangeRate(
        CurrencyCode sourceCurrency,
        CurrencyCode targetCurrency,
        BigDecimal rate,
        Instant effectiveAt
) implements Serializable, Comparable<ExchangeRate> {
    public static final int RATE_SCALE = 0;
    public static final RoundingMode RATE_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final MathContext INVERSION_CONTEXT = new MathContext(RATE_SCALE + 4, RATE_ROUNDING_MODE);

    public ExchangeRate {
        Objects.requireNonNull(sourceCurrency, "Source currency cannot be null");
        Objects.requireNonNull(targetCurrency, "Target currency cannot be null");
        Objects.requireNonNull(rate, "Exchange rate cannot be null");
        Objects.requireNonNull(effectiveAt, "Effective timestamp cannot be null");

        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Exchange rate must be strictly positive. Provided: " + rate);
        }

        if (sourceCurrency.equals(targetCurrency) && rate.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException("Exchange rate for identical currencies must be exactly 1.0");
        }

        rate = rate.setScale(RATE_SCALE, RATE_ROUNDING_MODE);
    }

    public static ExchangeRate of(
            CurrencyCode sourceCurrency,
            CurrencyCode targetCurrency,
            BigDecimal rate,
            Instant effectiveAt
    ) {
        return new ExchangeRate(sourceCurrency, targetCurrency, rate, effectiveAt);
    }

    public static ExchangeRate parity(CurrencyCode currencyCode, Instant effectiveAt) {
        return new ExchangeRate(currencyCode, currencyCode, BigDecimal.ONE, effectiveAt);
    }

    public Money convert(Money sourceMoney, Currency targetCurrencyMetadata) {
        Objects.requireNonNull(sourceMoney, "Source money cannot be null");
        Objects.requireNonNull(targetCurrencyMetadata, "Target currency metadata cannot be null");

        if (!sourceMoney.currencyCode().equals(this.sourceCurrency)) {
            throw new IllegalArgumentException(
                    "Source money currency mismatch. Expected: " + this.sourceCurrency + ", Got: " + sourceMoney.currencyCode()
            );
        }

        if (!targetCurrencyMetadata.getCode().equals(this.targetCurrency)) {
            throw new IllegalArgumentException(
                    "Target metadata currency mismatch. Expected: " + this.targetCurrency + ", Got: " + targetCurrencyMetadata.getCode()
            );
        }

        if (this.sourceCurrency.equals(this.targetCurrency)) {
            return sourceMoney;
        }

        BigDecimal convertedRawAmount = sourceMoney.amount().multiply(this.rate);

        return Money.of(convertedRawAmount, targetCurrencyMetadata);
    }


    public ExchangeRate invert() {
        if (sourceCurrency.equals(targetCurrency)) {
            return this;
        }

        BigDecimal invertedRate = BigDecimal.ONE.divide(this.rate, INVERSION_CONTEXT)
                .setScale(RATE_SCALE, RATE_ROUNDING_MODE);

        return new ExchangeRate(this.targetCurrency, this.sourceCurrency, invertedRate, this.effectiveAt);
    }

    @Override
    public int compareTo(ExchangeRate other) {
        int sourceComparison = this.sourceCurrency.compareTo(other.sourceCurrency);
        if (sourceComparison != 0) {
            return sourceComparison;
        }

        int targetComparison = this.targetCurrency.compareTo(other.targetCurrency);
        if (targetComparison != 0) {
            return targetComparison;
        }

        return this.effectiveAt.compareTo(other.effectiveAt);
    }

    @Override
    @NonNull
    public String toString() {
        return sourceCurrency.value() + "/" + targetCurrency.value() + " " + rate.toPlainString() + " @ " + effectiveAt;
    }

}
