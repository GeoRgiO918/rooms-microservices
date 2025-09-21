package com.georgiiHadzhiev.components;

import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.payloads.Payload;

import java.time.Instant;
import java.util.UUID;

public abstract class AbstractEventFactory {

    private final String source;
    private final String aggregateType;
    private final String topic;

    protected AbstractEventFactory(String source, String aggregateType, String topic) {
        this.source = source;
        this.aggregateType = aggregateType;
        this.topic = topic;
    }

    protected <T extends Payload> BaseEvent<T> buildEvent(
            UUID causation,
            String aggregateId,
            long version,
            String description,
            EventType eventType,
            T payload
    ) {
        return new BaseEvent<>(
                UUID.randomUUID(),
                causation,
                source,
                Instant.now(),
                aggregateId,
                aggregateType,
                version,
                description,
                eventType,
                payload,
                "USER SERVICE WORK IN PROGRESS",
                topic
        );
    }
}
