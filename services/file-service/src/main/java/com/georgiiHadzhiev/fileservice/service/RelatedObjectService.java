package com.georgiiHadzhiev.fileservice.service;

import com.georgiiHadzhiev.entity.CrudEventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.component.RelatedObjectMapper;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.fileservice.repository.RelatedObjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelatedObjectService {

    private final RelatedObjectRepository repository;
    private final RelatedObjectMapper mapper;

    public RelatedObjectService(RelatedObjectRepository repository, RelatedObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public boolean exists(RelatedObjectDto dto){
        Optional<RelatedObject> relatedObject=  repository.findByEntityTypeAndEntityId(dto.getEntityType(), dto.getEntityId());
        return relatedObject.isPresent();
    }

    @Transactional
    public RelatedObjectDto createRelatedObject(RelatedObjectDto dto) {

        RelatedObject relatedObject = mapper.toEntity(dto);
        repository.save(relatedObject);
        return mapper.toDto(relatedObject);

    }
    @Transactional
    public RelatedObjectDto deleteRelatedObject(RelatedObjectDto dto){
        Optional<RelatedObject> finded = repository.findByEntityTypeAndEntityId(dto.getEntityType(), dto.getEntityId());


        if(finded.isEmpty()) throw new EntityNotFoundException();
        RelatedObject deleted = finded.get();
        repository.deleteById(deleted.getId());

        return mapper.toDto(deleted);
    }


}
