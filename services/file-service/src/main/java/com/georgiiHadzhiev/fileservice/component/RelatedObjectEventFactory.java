package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.components.AbstractEventFactory;
import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.payloads.fileservice.RelatedObjectCreatedPayload;
import com.georgiiHadzhiev.payloads.fileservice.RelatedObjectSoftDeletedPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RelatedObjectEventFactory extends AbstractEventFactory {

    public RelatedObjectEventFactory() {
        super("file-service", "RelatedObject", "file.events");
    }

    public BaseEvent<RelatedObjectCreatedPayload> createRelatedObjectCreated(RelatedObject relatedObject, UUID causationId){
        BaseEvent<RelatedObjectCreatedPayload> event =  buildEvent(
                causationId,
                relatedObject.getEntityId(),
                1,
                relatedObject.getEntityType() + " created",
                EventType.RELATED_OBJECT_CREATED,
                new RelatedObjectCreatedPayload());
        event.setAggregateType("RelatedObject");
        return event;
    }
    public BaseEvent<RelatedObjectSoftDeletedPayload> createRelatedObjectSoftDeleted(RelatedObject relatedObject, UUID causationId){
        BaseEvent<RelatedObjectSoftDeletedPayload> event = buildEvent(
                causationId,
                relatedObject.getEntityId(),
                1,
                relatedObject.getEntityType() + " created",
                EventType.RELATED_OBJECT_SOFT_DELETED,
                new RelatedObjectSoftDeletedPayload());
        event.setAggregateType("RelatedObject");
        return event;
    }
}
