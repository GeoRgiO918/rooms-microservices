package com.georgiiHadzhiev.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.payloads.Payload;
import com.georgiiHadzhiev.payloads.fileservice.*;
import com.georgiiHadzhiev.payloads.roomservice.RoomCreatedPayload;
import com.georgiiHadzhiev.payloads.roomservice.RoomUpdatedPayload;
import com.georgiiHadzhiev.payloads.roomservice.RoomViewedPayload;

import java.time.Instant;
import java.util.UUID;

public class BaseEvent<T extends Payload> {

    UUID eventId;
    UUID causationId;
    String sourceService;
    Instant timestamp;
    String aggregateId;
    String aggregateType;
    long aggregateVersion;
    String description;
    EventType eventType;
    String userId;
    String topic;

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY, // <-- ключевой момент
            property = "eventType"                      // <-- смотри в eventType
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = RoomCreatedPayload.class, name = "ROOM_CREATED"),
            @JsonSubTypes.Type(value = RoomUpdatedPayload.class, name = "ROOM_UPDATED"),
            @JsonSubTypes.Type(value = RoomCreatedPayload.class, name = "ROOM_DELETED"),
            @JsonSubTypes.Type(value = RoomViewedPayload.class, name = "ROOM_VIEWED"),
            @JsonSubTypes.Type(value = FileUploadedPayload.class, name = "FILE_UPLOADED"),
            @JsonSubTypes.Type(value = RelatedObjectCreatedPayload.class, name = "RELATED_OBJECT_CREATED"),
            @JsonSubTypes.Type(value = RelatedObjectSoftDeletedPayload.class, name = "RELATED_OBJECT_SOFT_DELETED"),
            @JsonSubTypes.Type(value = FileSoftDeletedPayload.class, name = "FILE_SOFT_DELETED"),
            @JsonSubTypes.Type(value = FileMetadataViewedPayload.class, name = "FILEMETADATA_VIEWED"),
            @JsonSubTypes.Type(value = FileDownloadedPayload.class, name = "FILE_DOWNLOADED"),
            @JsonSubTypes.Type(value = FileDeletedPayload.class, name = "FILE_DELETED"),
    })

    T payload;

    public String getSourceService() {
        return sourceService;
    }

    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public BaseEvent(UUID eventId, UUID causationId, String sourceService, Instant timestamp, String aggregateId, String aggregateType, long aggregateVersion, String description, EventType eventType, T payload, String userId,String topic) {
        this.eventId = eventId;
        this.causationId = causationId;
        this.sourceService = sourceService;
        this.timestamp = timestamp;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.aggregateVersion = aggregateVersion;
        this.description =description;
        this.eventType = eventType;
        this.payload = payload;
        this.userId = userId;
        this.topic = topic;
    }

    public BaseEvent() {
    }
}
