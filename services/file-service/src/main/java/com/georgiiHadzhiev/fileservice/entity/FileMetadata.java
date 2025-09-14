package com.georgiiHadzhiev.fileservice.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false )
    private FileStatus status;

    @Column(name = "user_owner_id")
    private String userOwnerId;

    @Column(name = "file_type")
    private String fileType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_object_id" , nullable = false )
    private RelatedObject relatedObject;


    @Column(name = "size_bytes")
    private Long sizeBytes;

    @Column(name = "original_filename")
    private String originalFilename;

    @Column(name = "object_key")
    private String objectKey;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileUrl() { return getBucketName() + "/" + objectKey; }

    public FileStatus getStatus() { return status; }
    public void setStatus(FileStatus status) { this.status = status; }

    public String getUserOwnerId() { return userOwnerId; }
    public void setUserOwnerId(String userOwnerId) { this.userOwnerId = userOwnerId; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }


    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }

    public String getOriginalFilename() { return originalFilename; }
    public void setOriginalFilename(String originalFilename) { this.originalFilename = originalFilename; }

    public String getBucketName() {  return (relatedObject.getEntityType() + "bucket").toLowerCase();}

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public RelatedObject getRelatedObject() {
        return relatedObject;
    }

    public void setRelatedObject(RelatedObject relatedObject) {
        this.relatedObject = relatedObject;
    }
}
