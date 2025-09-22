package com.georgiiHadzhiev.fileservice.service;

import com.georgiiHadzhiev.events.BaseEvent;
import com.georgiiHadzhiev.fileservice.component.FileEventFactory;
import com.georgiiHadzhiev.fileservice.component.FileMetadataEventFactory;
import com.georgiiHadzhiev.fileservice.component.FileMetadataMapper;
import com.georgiiHadzhiev.fileservice.dto.FileApplicationEvent;
import com.georgiiHadzhiev.fileservice.dto.FileCreateMetadataDto;
import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.FileStatus;
import com.georgiiHadzhiev.fileservice.entity.RelatedObject;
import com.georgiiHadzhiev.fileservice.repository.FileMetadataRepository;
import com.georgiiHadzhiev.payloads.fileservice.FileMetadataViewedPayload;
import com.georgiiHadzhiev.payloads.fileservice.FileSoftDeletedPayload;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileMetadataService {

    private final RelatedObjectService relatedObjectService;
    private final FileMetadataRepository repository;
    private final FileMetadataMapper mapper;
    private final FileMetadataEventFactory eventFactory;
    private final ApplicationEventPublisher innerEventPublisher;

    public FileMetadataService(RelatedObjectService relatedObjectService,
                               FileMetadataRepository repository,
                               FileMetadataMapper mapper,
                               FileMetadataEventFactory eventFactory,
                               ApplicationEventPublisher innerEventPublisher) {
        this.relatedObjectService = relatedObjectService;
        this.repository = repository;
        this.mapper = mapper;
        this.eventFactory = eventFactory;
        this.innerEventPublisher = innerEventPublisher;
    }

    @Transactional
    public void scheduleFilesForDeletion(RelatedObjectDto dto, UUID causationEvent) {
        RelatedObject relatedObject = relatedObjectService.findEntityByDto(dto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "RelatedObject " + dto.getEntityId() + ":" + dto.getEntityType() + " does not exist"
                ));

        List<FileMetadata> metadataList = repository.findByRelatedObject(relatedObject);
        for (FileMetadata m : metadataList) {
            m.setStatus(FileStatus.TO_DELETE);
            BaseEvent<FileSoftDeletedPayload> event = eventFactory.createFileSoftDeleted(m,causationEvent);
            innerEventPublisher.publishEvent(new FileApplicationEvent(event));
        }
        repository.saveAll(metadataList);

    }

    @Transactional
    public FileMetadataDto createFileMetadataFromFile(MultipartFile file, FileCreateMetadataDto dto) {

        RelatedObjectDto relatedObjectDto = new RelatedObjectDto(dto.getRelatedEntityId(), dto.getRelatedEntityType());
        RelatedObject relatedObject = relatedObjectService.findEntityByDto(relatedObjectDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "RelatedObject " + relatedObjectDto.getEntityId() + ":" + relatedObjectDto.getEntityType() + " does not exist"
                ));


        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setRelatedObject(relatedObject);
        fileMetadata.setOriginalFilename(file.getOriginalFilename());
        fileMetadata.setFileType(file.getContentType());
        fileMetadata.setSizeBytes(file.getSize());
        String objectKey = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        fileMetadata.setObjectKey(objectKey);
        fileMetadata.setStatus(FileStatus.ACTIVE);
        fileMetadata.setUserOwnerId("USER");

        repository.save(fileMetadata);
        return mapper.toDto(fileMetadata);


    }

    @Transactional
    public FileMetadataDto getFileMetadataByStatusAndId(FileStatus status, long id) {
        FileMetadata fileMetadata = repository.findByStatusAndId(status, id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "fileMetadata with id:" + id + " does not exist"
                ));

        return mapper.toDto(fileMetadata);

    }

    @Transactional
    public FileMetadataDto scheduleForDelete(long id) {
        FileMetadata fileMetadata = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "fileMetadata with id:" + id + " does not exist"
                ));
        fileMetadata.setStatus(FileStatus.TO_DELETE);
        repository.save(fileMetadata);
        BaseEvent<FileSoftDeletedPayload> event = eventFactory.createFileSoftDeleted(fileMetadata,null);
        innerEventPublisher.publishEvent(new FileApplicationEvent(event));
        return mapper.toDto(fileMetadata);
    }

    @Transactional
    public FileMetadataDto updateFileMetadata(FileMetadataDto dto) {
        long id = dto.getId();
        Optional<FileMetadata> found = repository.findById(id);

        if (found.isEmpty()) {
            throw new EntityNotFoundException("FileMetadata by id:" + id + " not found");
        }
        FileMetadata fileMetadata = mapper.toEntity(dto);
        fileMetadata = repository.save(fileMetadata);

        return mapper.toDto(fileMetadata);

    }

    ;

    @Transactional
    public List<FileMetadataDto> findAllFileMetadataByStatus(FileStatus fileStatus) {
        List<FileMetadata> fileMetadata = repository.findAllByStatus(fileStatus);

        return fileMetadata.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public FileMetadataDto getFileMetadataById(long id) {
        FileMetadata metadata = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "fileMetadata with id:" + id + " does not exist"
                ));

        BaseEvent<FileMetadataViewedPayload> event = eventFactory.createFileMetadataViewed(metadata);
        innerEventPublisher.publishEvent(new FileApplicationEvent(event));
        return mapper.toDto(metadata);

    }

}
