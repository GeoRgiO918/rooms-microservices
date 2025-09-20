package com.georgiiHadzhiev.exceptions;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.payloads.Payload;

public class EventPublishingException extends RuntimeException {
    public EventPublishingException(String message) {
        super(message);
    }

    public EventPublishingException(BaseEvent<? extends Payload> event){
        super(buildMessage(event));
    }




        private static String buildMessage(BaseEvent<? extends Payload> event) {
            return String.format(
                    "Error publishing event: [eventId=%s, causationId=%s, type=%s, aggregateType=%s, aggregateId=%s, version=%d, author=%s, timestamp=%s]",
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
