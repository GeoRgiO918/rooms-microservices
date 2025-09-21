package com.georgiiHadzhiev.fileservice.dto;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.payloads.Payload;

public class FileApplicationEvent {

    BaseEvent<? extends Payload> outerEvent;

    public FileApplicationEvent(BaseEvent<? extends Payload> outerEvent) {
        this.outerEvent = outerEvent;
    }

    public BaseEvent<? extends  Payload> getOuterEvent(){
        return outerEvent;
    }
}
