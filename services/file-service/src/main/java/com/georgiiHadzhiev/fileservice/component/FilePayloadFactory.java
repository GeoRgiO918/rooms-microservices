package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.components.AbstractPayloadFactory;
import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.payloads.fileservice.FileDownloadedPayload;
import com.georgiiHadzhiev.payloads.fileservice.FileUploadedPayload;
import org.springframework.stereotype.Component;
;

@Component
public class FilePayloadFactory extends AbstractPayloadFactory {


    public FileUploadedPayload createFileUploadedPayload(FileMetadataDto fileMetadata){
        return new FileUploadedPayload(
                fileMetadata.getRelatedEntityType(),
                fileMetadata.getRelatedEntityId(),
                fileMetadata.getStatus().name(),
                fileMetadata.getFileType(),
                fileMetadata.getOriginalFilename(),
                fileMetadata.getObjectKey(),
                fileMetadata.getSizeBytes()
        );
    }

    public FileDownloadedPayload createFileDownloadedPayload(FileMetadataDto fileMetadata){
        return new FileDownloadedPayload(
                fileMetadata.getOriginalFilename(),
                fileMetadata.getFileType(),
                fileMetadata.getSizeBytes(),
                fileMetadata.getFileUrl());
    }
}
