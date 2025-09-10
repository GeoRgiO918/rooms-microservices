package com.georgiiHadzhiev.exceptions;

import com.georgiiHadzhiev.events.BaseEvent;

public class EventPublishingException extends RuntimeException {
    public EventPublishingException(String message) {
        super(message);
    }

    public EventPublishingException(BaseEvent event){
        super(buildMessage(event));
    }




        private static String buildMessage(BaseEvent event) {
            return String.format(
                    "Error publishing event: [eventId=%s, causationId=%s, type=%s, aggregateType=%s, aggregateId=%s, version=%d, author=%s, timestamp=%s]",
                    event.getEventId(),
                    event.getCausationId(),
                    event.getType(),
                    event.getAggregateType(),
                    event.getAggregateId(),
                    event.getAggregateVersion(),
                    event.getAuthor(),
                    event.getTimestamp()
            );
        }


}
