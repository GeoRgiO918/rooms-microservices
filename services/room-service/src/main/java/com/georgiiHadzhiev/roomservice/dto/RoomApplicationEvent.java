package com.georgiiHadzhiev.roomservice.dto;

import com.georgiiHadzhiev.events.BaseEvent;

public class RoomApplicationEvent {

    BaseEvent outerEvent;

    public RoomApplicationEvent(BaseEvent outerEvent) {
        this.outerEvent = outerEvent;
    }

    public BaseEvent getOuterEvent(){
        return outerEvent;
    }
}
