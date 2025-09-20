package com.georgiiHadzhiev.exceptions;

import com.georgiiHadzhiev.events.BaseEvent;

public class EventConsumingException  extends RuntimeException{

    public EventConsumingException(String message) {
        super(message);
    }

    public EventConsumingException(BaseEvent event){
        super(buildMessage(event));
    }




    private static String buildMessage(BaseEvent event) {
        return String.format(
                "Error consuming event: [eventId=%s, causationId=%s, type=%s, aggregateType=%s, aggregateId=%s, version=%d, author=%s, timestamp=%s]",
                event.getEventId(),
                event.getCausationId(),
                event.getEventType(),
                event.getAggregateType(),
                event.getAggregateId(),
                event.getAggregateVersion(),
                event.getSourceService(),
                event.getTimestamp()
        );
    }
}
