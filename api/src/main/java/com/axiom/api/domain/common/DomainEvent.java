package com.axiom.api.domain.common;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface DomainEvent extends Serializable {
    UUID eventId();

    Instant occurredAt();

    String aggregateType(); /*The business type of the aggregate root that published this event(e.g., "VOUCHER", "ACCOUNT", "USER")*/

    String aggregateId(); /*The business identifier of the aggregate root (e.g., voucher ID or account ID).*/

    default String eventType() {
        return getClass().getSimpleName();     /*The canonical name of the event used for event routing, serialization headers and message broker topic/routing keys.*/
    }
}
