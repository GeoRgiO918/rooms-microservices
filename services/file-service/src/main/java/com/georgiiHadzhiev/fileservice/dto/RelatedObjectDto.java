package com.georgiiHadzhiev.fileservice.dto;

public class RelatedObjectDto {


    private String entityType;

    private String entityId;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public RelatedObjectDto(String entityId, String entityType) {
        this.entityId = entityId;
        this.entityType = entityType;
    }
    public RelatedObjectDto(){

    };
}
