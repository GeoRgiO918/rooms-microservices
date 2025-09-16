package com.georgiiHadzhiev.fileservice.service;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.exceptions.EventConsumingException;
import com.georgiiHadzhiev.fileservice.component.EventToDtoMapper;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EventProccesingService {

    private final EventToDtoMapper eventMapper;
    private final RelatedObjectService relatedObjectService;
    private final FileMetadataService metadataService;


    public EventProccesingService(EventToDtoMapper eventMapper, RelatedObjectService relatedObjectService, FileMetadataService metadataService) {
        this.eventMapper = eventMapper;
        this.relatedObjectService = relatedObjectService;
        this.metadataService = metadataService;
    }


    @Transactional
    public void process(BaseEvent event) {
        RelatedObjectDto dto = eventMapper.fromBaseEvent(event);
        boolean isEntityExists = relatedObjectService.exists(dto);

        switch (event.getType()) {
            case READ:
                return;
            case CREATED:
            case UPDATED:
                if (!isEntityExists) relatedObjectService.createRelatedObject(dto);
                return;
            case DELETED:
                if (isEntityExists) {
                    metadataService.scheduleFilesForDeletion(dto);
                    relatedObjectService.deleteRelatedObject(dto);


                }
                return;
            default:
                throw new EventConsumingException(event);
        }


    }
}
