package com.georgiiHadzhiev.events;

import com.georgiiHadzhiev.entity.EventType;

import java.time.LocalDateTime;

public class BaseEvent {

    String author;
    LocalDateTime timestamp;
    EventType type;

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

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public BaseEvent(String author, LocalDateTime timestamp, EventType type) {
        this.author = author;
        this.timestamp = timestamp;
        this.type = type;
    }
}
