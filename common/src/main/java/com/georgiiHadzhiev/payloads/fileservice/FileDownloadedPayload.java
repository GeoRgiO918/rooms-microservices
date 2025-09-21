package com.georgiiHadzhiev.payloads.fileservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.georgiiHadzhiev.payloads.Payload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDownloadedPayload implements Payload {
    private String originalFilename;
    private String fileType;
    private long sizeBytes;
    private String fileUrl;

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public FileDownloadedPayload(String originalFilename, String fileType, long sizeBytes, String fileUrl) {
        this.originalFilename = originalFilename;
        this.fileType = fileType;
        this.sizeBytes = sizeBytes;
        this.fileUrl = fileUrl;
    }

    public FileDownloadedPayload() {
    }
}
