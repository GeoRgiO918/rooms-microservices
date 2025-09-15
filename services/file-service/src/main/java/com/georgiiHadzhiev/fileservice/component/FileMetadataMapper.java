package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.fileservice.repository.RelatedObjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class FileMetadataMapper {

    private final RelatedObjectRepository repository;

    public FileMetadataMapper(RelatedObjectRepository repository) {
        this.repository = repository;
    }

    public FileMetadataDto toDto(FileMetadata fileMetadata){
        FileMetadataDto dto = new FileMetadataDto();
        dto.setObjectKey(fileMetadata.getObjectKey());
        dto.setFileType(fileMetadata.getFileType());
        dto.setId(fileMetadata.getId());
        dto.setStatus(fileMetadata.getStatus());
        dto.setRelatedEntityId(fileMetadata.getRelatedObject().getEntityId());
        dto.setRelatedEntityType(fileMetadata.getRelatedObject().getEntityType());
        dto.setOriginalFilename(fileMetadata.getOriginalFilename());
        dto.setSizeBytes(fileMetadata.getSizeBytes());
        return dto;
    }

    @Transactional
    public FileMetadata toEntity(FileMetadataDto dto){
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setObjectKey(dto.getObjectKey());
        fileMetadata.setFileType(dto.getFileType());
        fileMetadata.setId(dto.getId());
        fileMetadata.setStatus(dto.getStatus());
        fileMetadata.setOriginalFilename(dto.getOriginalFilename());
        fileMetadata.setSizeBytes(dto.getSizeBytes());


        RelatedObject relatedObject = repository
                .findByEntityTypeAndEntityId(dto.getRelatedEntityType(), dto.getRelatedEntityId())
                .orElseThrow(() -> new EntityNotFoundException("Related object not found"));
        fileMetadata.setRelatedObject(relatedObject);
        return fileMetadata;
    }
}
