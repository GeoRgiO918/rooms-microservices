package com.georgiiHadzhiev.roomservice.dto;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.payloads.Payload;

public class RoomApplicationEvent {

    BaseEvent<? extends Payload> outerEvent;

    public RoomApplicationEvent(BaseEvent<? extends Payload> outerEvent) {
        this.outerEvent = outerEvent;
    }

    public BaseEvent<? extends Payload> getOuterEvent(){
        return outerEvent;
    }
}
