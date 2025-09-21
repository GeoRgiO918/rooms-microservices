package com.georgiiHadzhiev.payloads.fileservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.georgiiHadzhiev.payloads.Payload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadedPayload implements Payload {

    private String relatedEntityType;
    private String relatedEntityId;
    private String status;
    private String fileType;
    private String originalFilename;
    private String objectKey;
    private Long sizeBytes;



    public String getRelatedEntityType() {
        return relatedEntityType;
    }

    public void setRelatedEntityType(String relatedEntityType) {
        this.relatedEntityType = relatedEntityType;
    }

    public String getRelatedEntityId() {
        return relatedEntityId;
    }

    public void setRelatedEntityId(String relatedEntityId) {
        this.relatedEntityId = relatedEntityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public FileUploadedPayload(String relatedEntityType, String relatedEntityId, String status, String fileType, String originalFilename, String objectKey, Long sizeBytes) {
        this.relatedEntityType = relatedEntityType;
        this.relatedEntityId = relatedEntityId;
        this.status = status;
        this.fileType = fileType;
        this.originalFilename = originalFilename;
        this.objectKey = objectKey;
        this.sizeBytes = sizeBytes;
    }

    public FileUploadedPayload() {
    }
}
