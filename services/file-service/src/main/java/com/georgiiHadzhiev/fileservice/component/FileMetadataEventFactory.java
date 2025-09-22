package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.components.AbstractEventFactory;
import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.payloads.fileservice.FileMetadataViewedPayload;
import com.georgiiHadzhiev.payloads.fileservice.FileSoftDeletedPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FileMetadataEventFactory extends AbstractEventFactory {

    public FileMetadataEventFactory(
    ) {
        super("file-service", "FileMetadata", "file.events");
    }

    public BaseEvent<FileMetadataViewedPayload> createFileMetadataViewed(FileMetadata fileMetadata){
        return buildEvent(
                null,
                fileMetadata.getId().toString(),
                1,
                "File marked to delete",
                EventType.FILEMETADATA_VIEWED,
                new FileMetadataViewedPayload()
        );
    }

    public BaseEvent<FileSoftDeletedPayload> createFileSoftDeleted(FileMetadata fileMetadata, UUID causationId){
        return buildEvent(
                causationId,
                fileMetadata.getId().toString(),
                1,
                "File marked to delete",
                EventType.FILE_SOFT_DELETED,
                new FileSoftDeletedPayload()
        );
    }
}
