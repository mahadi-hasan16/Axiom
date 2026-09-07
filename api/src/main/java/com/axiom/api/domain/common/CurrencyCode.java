package com.axiom.api.domain.common;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record CurrencyCode(String value) implements Serializable, Comparable<CurrencyCode> {
    private static Pattern ISO_4217_PATTERN = Pattern.compile("^[A-Z]{3}$");

    public CurrencyCode{
        Objects.requireNonNull(value, "Currency Code String Can not be null");
        value = value.strip().toUpperCase(Locale.ROOT);
        if(!ISO_4217_PATTERN.matcher(value).matches()){
            throw new IllegalArgumentException("Invalid ISO 4217 Currency Code String. Currency code must be exactly 3 uppercase ASCII letters: "+value);
        }
    }

    public static CurrencyCode of(String value){
        return new CurrencyCode(value);
    }

    @Override
    public int compareTo(CurrencyCode other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value;
    }
}
