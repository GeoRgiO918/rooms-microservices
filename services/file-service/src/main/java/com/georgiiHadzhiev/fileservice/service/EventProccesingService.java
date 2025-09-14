package com.georgiiHadzhiev.fileservice.service;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.exceptions.EventConsumingException;
import com.georgiiHadzhiev.fileservice.component.EventToDtoMapper;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EventProccesingService {

    private final EventToDtoMapper eventMapper;
    private final RelatedObjectService relatedObjectService;


    public EventProccesingService(EventToDtoMapper eventMapper,RelatedObjectService relatedObjectService){
        this.eventMapper = eventMapper;
        this.relatedObjectService =relatedObjectService;
    }


    @Transactional
    public void process(BaseEvent event){
        RelatedObjectDto dto = eventMapper.fromBaseEvent(event);
        boolean isEntityExists = relatedObjectService.exists(dto);

        switch (event.getType()){
            case READ:
                return;
            case CREATED:
            case UPDATED:
                if(!isEntityExists) relatedObjectService.createRelatedObject(dto);
                return;
            case DELETED:
                if(isEntityExists) relatedObjectService.deleteRelatedObject(dto);
                return;
            default:
                throw new EventConsumingException(event);
        }


    }
}
