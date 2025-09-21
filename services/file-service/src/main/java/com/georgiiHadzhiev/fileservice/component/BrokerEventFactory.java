package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.components.AbstractEventFactory;
import com.georgiiHadzhiev.entity.EventType;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.payloads.fileservice.*;

import java.util.UUID;

public class BrokerEventFactory extends AbstractEventFactory {

    private final FilePayloadFactory payloadFactory;

    public BrokerEventFactory(FilePayloadFactory payloadFactory) {
        super("file-service","File","file.events");
        this.payloadFactory = payloadFactory;
    }

    public BaseEvent<FileUploadedPayload> createFileUploaded(FileMetadata fileMetadata){
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

    public BaseEvent<RelatedObjectCreatedPayload> createRelatedObjectCreated(RelatedObject relatedObject, UUID causationId){
        BaseEvent<RelatedObjectCreatedPayload> event =  buildEvent(
                causationId,
                relatedObject.getEntityId(),
                1,
                relatedObject.getEntityType() + " created",
                EventType.RELATED_OBJECT_CREATED,
                new RelatedObjectCreatedPayload());
        event.setAggregateType("RelatedObject");
        return event;
    }
    public BaseEvent<RelatedObjectSoftDeletedPayload> createRelatedObjectSoftDeleted(RelatedObject relatedObject, UUID causationId){
        BaseEvent<RelatedObjectSoftDeletedPayload> event = buildEvent(
                causationId,
                relatedObject.getEntityId(),
                1,
                relatedObject.getEntityType() + " created",
                EventType.RELATED_OBJECT_SOFT_DELETED,
                new RelatedObjectSoftDeletedPayload());
        event.setAggregateType("RelatedObject");
        return event;
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
    public BaseEvent<FileDeletedPayload> createFileDeleted(FileMetadata fileMetadata){
        return buildEvent(
                null,
                fileMetadata.getId().toString(),
                1,
                "File was deleted from storage",
                EventType.FILE_DELETED,
                new FileDeletedPayload()
        );
    }
    public BaseEvent<FileDownloadedPayload> createFileDownloaded(FileMetadata fileMetadata){
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
