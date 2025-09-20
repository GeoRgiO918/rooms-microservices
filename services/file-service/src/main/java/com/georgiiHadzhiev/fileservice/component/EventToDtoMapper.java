package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.payloads.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventToDtoMapper {

    public RelatedObjectDto fromBaseEvent(BaseEvent<? extends Payload> event){
        RelatedObjectDto dto = new RelatedObjectDto();
        dto.setEntityId(event.getAggregateId());
        dto.setEntityType(event.getAggregateType());

        return dto;

    }
}
