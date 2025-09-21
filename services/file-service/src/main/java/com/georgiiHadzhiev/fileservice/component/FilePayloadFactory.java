package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.components.AbstractPayloadFactory;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.payloads.fileservice.FileDownloadedPayload;
import com.georgiiHadzhiev.payloads.fileservice.FileUploadedPayload;
;

public class FilePayloadFactory extends AbstractPayloadFactory {


    public FileUploadedPayload createFileUploadedPayload(FileMetadata fileMetadata){
        return new FileUploadedPayload(
                fileMetadata.getRelatedObject().getEntityType(),
                fileMetadata.getRelatedObject().getEntityId(),
                fileMetadata.getStatus().name(),
                fileMetadata.getFileType(),
                fileMetadata.getOriginalFilename(),
                fileMetadata.getObjectKey(),
                fileMetadata.getSizeBytes()
        );
    }

    public FileDownloadedPayload createFileDownloadedPayload(FileMetadata fileMetadata){
        return new FileDownloadedPayload(
                fileMetadata.getOriginalFilename(),
                fileMetadata.getFileType(),
                fileMetadata.getSizeBytes(),
                fileMetadata.getFileUrl());
    }
}
