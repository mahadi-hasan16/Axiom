package com.axiom.api.domain.common;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public abstract class DomainException extends RuntimeException {

    private final String errorCode;
    private final Map<String, Object> details;

    protected DomainException(String errorCode, String message) {
        this(errorCode, message, Collections.emptyMap(), null);
    }

    protected DomainException(String errorCode, String message, Map<String, Object> details) {
        this(errorCode, message, details, null);
    }

    protected DomainException(String errorCode, String message, Throwable cause) {
        this(errorCode, message, Collections.emptyMap(), cause);
    }

    protected DomainException(String errorCode, String message, Map<String, Object> details, Throwable cause) {
        super(Objects.requireNonNull(message, "Exception message cannot be null"), cause);
        this.errorCode = Objects.requireNonNull(errorCode, "Error code cannot be null").strip();
        this.details = details != null ? Map.copyOf(details) : Collections.emptyMap();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "[" + errorCode + "] " + getMessage() + (details.isEmpty() ? "" : " | Context: " + details);
    }
}