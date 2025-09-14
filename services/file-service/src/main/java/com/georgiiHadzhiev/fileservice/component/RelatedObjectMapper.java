package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import org.springframework.stereotype.Component;

@Component
public class RelatedObjectMapper {



    public RelatedObjectDto toDto(RelatedObject relatedObject){
        RelatedObjectDto dto = new RelatedObjectDto();
        dto.setEntityId(relatedObject.getEntityId());
        dto.setEntityType(relatedObject.getEntityType());

        return dto;
    }

    public RelatedObject toEntity(RelatedObjectDto dto){
        RelatedObject relatedObject = new RelatedObject();
        relatedObject.setEntityId(dto.getEntityId());
        relatedObject.setEntityType(dto.getEntityType());

        return relatedObject;
    }

    }
