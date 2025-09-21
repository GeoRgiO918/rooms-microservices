package com.georgiiHadzhiev.payloads.fileservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.georgiiHadzhiev.payloads.Payload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelatedObjectCreatedPayload implements Payload {
    public RelatedObjectCreatedPayload() {
    }
}
