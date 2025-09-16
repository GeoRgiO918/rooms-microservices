package com.georgiiHadzhiev.fileservice.controller;

import com.georgiiHadzhiev.fileservice.dto.FileCreateMetadataDto;
import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.FileStatus;
import com.georgiiHadzhiev.fileservice.service.FileMetadataService;
import com.georgiiHadzhiev.fileservice.service.FileService;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/file")
public class FileController {

    public final FileService fileService;
    public final FileMetadataService metadataService;
    private final S3Client s3Client;

    public FileController(FileService fileService, FileMetadataService metadataService,S3Client s3Client) {
        this.fileService = fileService;
        this.metadataService = metadataService;
        this.s3Client =s3Client;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileMetadataDto> uploadFile(
            @RequestPart("metadata") FileCreateMetadataDto dto,
            @RequestPart("file") MultipartFile file) {
        try {
            FileMetadataDto fileMetadataDto = fileService.uploadFile(file, dto);
            URI location = URI.create("/file/" + fileMetadataDto.getId());
            return ResponseEntity.created(location).body(fileMetadataDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long id) {
        try {
            FileMetadataDto fileMetadata = metadataService.getFileMetadataByStatusAndId(FileStatus.ACTIVE,id);
            InputStreamResource resource = fileService.getFileInputStream(fileMetadata);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMetadata.getOriginalFilename() + "\"")
                    .contentType(MediaType.parseMediaType(fileMetadata.getFileType()))
                    .contentLength(fileMetadata.getSizeBytes())
                    .body(resource);

        } catch (NoSuchKeyException | EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FileMetadataDto> softdeleteFile(@PathVariable Long id){
        FileMetadataDto fileMetadataDto = null;
        try {
             fileMetadataDto = metadataService.scheduleForDelete(id);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fileMetadataDto);
    }

    @GetMapping
    public ResponseEntity<List<FileMetadataDto>> getAllFileMetadata(@RequestParam(required = false) FileStatus status){
        List<FileMetadataDto> found = metadataService.findAllFileMetadataByStatus(status == null ? FileStatus.ACTIVE: status);
        if(found.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileMetadataDto> getFileMetadata(@PathVariable long id){
        FileMetadataDto metadata = null;
        try {
            metadata = metadataService.getFileMetadataById(id);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metadata);
    }


}
