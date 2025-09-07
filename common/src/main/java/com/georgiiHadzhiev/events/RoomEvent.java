package com.georgiiHadzhiev.events;


import com.georgiiHadzhiev.entity.EventType;

import java.time.LocalDateTime;

public class RoomEvent extends  BaseEvent {
    long roomId;

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public RoomEvent(long roomId, String author, LocalDateTime timestamp, EventType type){
        super(author, timestamp, type);
        this.roomId = roomId;
    }
}
