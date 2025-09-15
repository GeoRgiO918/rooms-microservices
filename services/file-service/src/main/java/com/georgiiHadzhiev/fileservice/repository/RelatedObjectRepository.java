package com.georgiiHadzhiev.fileservice.repository;

import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RelatedObjectRepository extends JpaRepository<RelatedObject,Long> {

    public Optional<RelatedObject> findByEntityTypeAndEntityIdAndDeletedAtIsNull(String entityType, String entityId);

    public Optional<RelatedObject> findByEntityTypeAndEntityId(String entityType, String entityId);

}
