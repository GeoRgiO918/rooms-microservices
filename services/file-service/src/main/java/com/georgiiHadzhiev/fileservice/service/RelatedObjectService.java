package com.georgiiHadzhiev.fileservice.service;

import com.georgiiHadzhiev.fileservice.component.RelatedObjectMapper;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.fileservice.repository.FileMetadataRepository;
import com.georgiiHadzhiev.fileservice.repository.RelatedObjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        Optional<RelatedObject> relatedObject= findEntityByDto(dto);
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
        Optional<RelatedObject> found = findEntityByDto(dto);


        if(found.isEmpty()) throw new EntityNotFoundException();
        RelatedObject deleted = found.get();
        deleted.setDeletedAt(LocalDateTime.now());
        repository.save(deleted);

        return mapper.toDto(deleted);
    }

    @Transactional
    public Optional<RelatedObject> findEntityByDto(RelatedObjectDto dto){
        return repository.findByEntityTypeAndEntityIdAndDeletedAtIsNull(dto.getEntityType(), dto.getEntityId());
    }




}
