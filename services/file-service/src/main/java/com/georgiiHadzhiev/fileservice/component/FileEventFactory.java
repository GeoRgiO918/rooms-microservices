package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.components.AbstractEventFactory;
import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.payloads.fileservice.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileEventFactory extends AbstractEventFactory {

    private final FilePayloadFactory payloadFactory;

    public FileEventFactory(FilePayloadFactory payloadFactory) {
        super("file-service","File","file.events");
        this.payloadFactory = payloadFactory;
    }

    public BaseEvent<FileUploadedPayload> createFileUploaded(FileMetadataDto fileMetadata){
        FileUploadedPayload payload = payloadFactory.createFileUploadedPayload(fileMetadata);

        return buildEvent(
                null,
                fileMetadata.getId().toString(),
                1,
                "File uploaded to storage",
                EventType.FILE_UPLOADED,
                payload
        );
    }




    public BaseEvent<FileDeletedPayload> createFileDeleted(FileMetadataDto fileMetadata){
        return buildEvent(
                null,
                fileMetadata.getId().toString(),
                1,
                "File was deleted from storage",
                EventType.FILE_DELETED,
                new FileDeletedPayload()
        );
    }
    public BaseEvent<FileDownloadedPayload> createFileDownloaded(FileMetadataDto fileMetadata){
        FileDownloadedPayload payload = payloadFactory.createFileDownloadedPayload(fileMetadata);
        return buildEvent(
                null,
                fileMetadata.getId().toString(),
                1,
                "File was downloaded",
                EventType.FILE_DOWNLOADED,
                payload
        );
    }
}
