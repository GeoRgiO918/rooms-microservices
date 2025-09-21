package com.georgiiHadzhiev.fileservice.controller;

import com.georgiiHadzhiev.fileservice.dto.FileCreateMetadataDto;
import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.entity.FileStatus;
import com.georgiiHadzhiev.fileservice.service.FileMetadataService;
import com.georgiiHadzhiev.fileservice.service.FileService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {

    public final FileService fileService;
    public final FileMetadataService metadataService;
    private final S3Client s3Client;

    private final static Logger log = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService, FileMetadataService metadataService, S3Client s3Client) {
        this.fileService = fileService;
        this.metadataService = metadataService;
        this.s3Client = s3Client;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileMetadataDto> uploadFile(
            @RequestPart("metadata") FileCreateMetadataDto dto,
            @RequestPart("file") MultipartFile file) {
        log.info("Received uploading file request: {} for entity {}", file.getOriginalFilename(),dto.getEntityKey());
        try {
            FileMetadataDto fileMetadataDto = fileService.uploadFile(file, dto);
            URI location = URI.create("/file/" + fileMetadataDto.getId());
            log.info("File uploaded successfully: {}", fileMetadataDto.getId());
            return ResponseEntity.created(location).body(fileMetadataDto);
        } catch (EntityNotFoundException e) {
            log.warn("Entity not found for upload: {}", dto.getEntityKey());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error uploading file: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long id) {
        log.info("Received file download request with id: {}", id);
        try {
            FileMetadataDto fileMetadata = metadataService.getFileMetadataByStatusAndId(FileStatus.ACTIVE, id);
            InputStreamResource resource = fileService.getFileInputStream(fileMetadata);
            log.info("File download prepared: {}", id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMetadata.getOriginalFilename() + "\"")
                    .contentType(MediaType.parseMediaType(fileMetadata.getFileType()))
                    .contentLength(fileMetadata.getSizeBytes())
                    .body(resource);
        } catch (NoSuchKeyException | EntityNotFoundException e) {
            log.warn("File not found for download: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FileMetadataDto> softDeleteFile(@PathVariable Long id) {
        log.info("Received soft delete request for file: {}", id);
        try {
            FileMetadataDto fileMetadataDto = metadataService.scheduleForDelete(id);
            log.info("File scheduled for deletion: {}", id);
            return ResponseEntity.ok(fileMetadataDto);
        } catch (EntityNotFoundException e) {
            log.warn("File not found for deletion: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<FileMetadataDto>> getAllFileMetadata(@RequestParam(required = false) FileStatus status) {
        FileStatus filterStatus = status == null ? FileStatus.ACTIVE : status;
        log.info("Fetching all file metadata with status: {}", filterStatus);
        List<FileMetadataDto> found = metadataService.findAllFileMetadataByStatus(filterStatus);
        if (found.isEmpty()) {
            log.info("No files found with status: {}", filterStatus);
            return ResponseEntity.notFound().build();
        }
        log.info("Found {} files with status: {}", found.size(), filterStatus);
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileMetadataDto> getFileMetadata(@PathVariable long id) {
        log.info("Fetching metadata for file id: {}", id);
        try {
            FileMetadataDto metadata = metadataService.getFileMetadataById(id);
            log.info("Metadata retrieved for file id: {}", id);
            return ResponseEntity.ok(metadata);
        } catch (EntityNotFoundException e) {
            log.warn("File metadata not found for id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}
