package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import org.springframework.stereotype.Component;

@Component
public class FileMetadataMapper {


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
}
