package com.georgiiHadzhiev.fileservice.repository;

import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata,Long> {



}
