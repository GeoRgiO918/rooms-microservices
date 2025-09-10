package com.georgiiHadzhiev.events;

import com.georgiiHadzhiev.entity.CrudEventType;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseEvent {

    UUID eventId;
    UUID causationId;
    String author;
    LocalDateTime timestamp;
    CrudEventType type;
    String aggregateId;
    String aggregateType;
    long aggregateVersion;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public CrudEventType getType() {
        return type;
    }

    public void setType(CrudEventType type) {
        this.type = type;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public UUID getCausationId() {
        return causationId;
    }

    public void setCausationId(UUID causationId) {
        this.causationId = causationId;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public long getAggregateVersion() {
        return aggregateVersion;
    }

    public void setAggregateVersion(long aggregateVersion) {
        this.aggregateVersion = aggregateVersion;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public BaseEvent(UUID eventId, UUID causationId, String author, LocalDateTime timestamp, CrudEventType type, String aggregateId, String aggregateType, long aggregateVersion) {
        this.eventId = eventId;
        this.causationId = causationId;
        this.author = author;
        this.timestamp = timestamp;
        this.type = type;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.aggregateVersion = aggregateVersion;
    }
}
