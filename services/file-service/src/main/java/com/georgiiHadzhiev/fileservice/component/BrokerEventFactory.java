package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.components.AbstractEventFactory;
import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.payloads.Payload;

public class BrokerEventFactory extends AbstractEventFactory {

    private final FilePayloadFactory payloadFactory;

    public BrokerEventFactory(FilePayloadFactory payloadFactory) {
        super("file-service","File","file.events");
        this.payloadFactory = payloadFactory;
    }


    public BaseEvent<? extends Payload> createFileUploaded(FileMetadata fileMetadata){

    }
}
