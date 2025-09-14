package com.georgiiHadzhiev.fileservice.repository;

import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.FileStatus;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata,Long> {

    public List<FileMetadata> findByRelatedObject(RelatedObject relatedObject);

    public Optional<FileMetadata> findByStatusAndId(FileStatus fileStatus, Long id);

}
