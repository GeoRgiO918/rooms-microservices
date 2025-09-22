package com.georgiiHadzhiev.fileservice.service;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.component.RelatedObjectEventFactory;
import com.georgiiHadzhiev.fileservice.component.RelatedObjectMapper;
import com.georgiiHadzhiev.fileservice.dto.FileApplicationEvent;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.fileservice.repository.FileMetadataRepository;
import com.georgiiHadzhiev.fileservice.repository.RelatedObjectRepository;
import com.georgiiHadzhiev.payloads.fileservice.RelatedObjectCreatedPayload;
import com.georgiiHadzhiev.payloads.fileservice.RelatedObjectSoftDeletedPayload;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RelatedObjectService {

    private final RelatedObjectRepository repository;
    private final RelatedObjectMapper mapper;
    private final RelatedObjectEventFactory eventFactory;
    private final ApplicationEventPublisher innerEventPublisher;

    public RelatedObjectService(RelatedObjectRepository repository, RelatedObjectMapper mapper, RelatedObjectEventFactory eventFactory, ApplicationEventPublisher innerEventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.eventFactory = eventFactory;
        this. innerEventPublisher = innerEventPublisher;
    }

    public boolean exists(RelatedObjectDto dto){
        Optional<RelatedObject> relatedObject= findEntityByDto(dto);
        return relatedObject.isPresent();
    }

    @Transactional
    public RelatedObjectDto createRelatedObject(RelatedObjectDto dto, UUID causationId) {

        RelatedObject relatedObject = mapper.toEntity(dto);
        repository.save(relatedObject);
        BaseEvent<RelatedObjectCreatedPayload> event = eventFactory.createRelatedObjectCreated(relatedObject,causationId);
        innerEventPublisher.publishEvent(new FileApplicationEvent(event));
        return mapper.toDto(relatedObject);

    }
    @Transactional
    public RelatedObjectDto deleteRelatedObject(RelatedObjectDto dto,UUID causationId){
        Optional<RelatedObject> found = findEntityByDto(dto);


        if(found.isEmpty()) throw new EntityNotFoundException();
        RelatedObject deleted = found.get();
        deleted.setDeletedAt(LocalDateTime.now());
        repository.save(deleted);
        BaseEvent<RelatedObjectSoftDeletedPayload> event = eventFactory.createRelatedObjectSoftDeleted(deleted,causationId);
        innerEventPublisher.publishEvent(new FileApplicationEvent(event));

        return mapper.toDto(deleted);
    }

    @Transactional
    public Optional<RelatedObject> findEntityByDto(RelatedObjectDto dto){
        return repository.findByEntityTypeAndEntityIdAndDeletedAtIsNull(dto.getEntityType(), dto.getEntityId());
    }




}
