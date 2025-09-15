package com.georgiiHadzhiev.fileservice.service;

import com.georgiiHadzhiev.fileservice.dto.FileCreateMetadataDto;
import com.georgiiHadzhiev.fileservice.dto.FileMetadataDto;
import com.georgiiHadzhiev.fileservice.dto.RelatedObjectDto;
import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.FileStatus;
import com.georgiiHadzhiev.fileservice.repository.FileMetadataRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    private final S3Client s3Client;
    private final FileMetadataService fileMetadataService;

    public FileService(S3Client s3Client, FileMetadataService fileMetadataService) {
        this.s3Client = s3Client;
        this.fileMetadataService = fileMetadataService;
    }

    @Transactional
    public FileMetadataDto uploadFile(MultipartFile file, FileCreateMetadataDto dto) {

        FileMetadataDto metadataDto = fileMetadataService.createFileMetadataFromFile(file,dto);

        try{
            uploadToStorage(file,metadataDto.getBucketName(),metadataDto.getObjectKey());
        }catch (Exception e){
            throw e;
        }


        return metadataDto;


    }

    public InputStreamResource getFileInputStream(FileMetadataDto dto){

        ResponseInputStream<GetObjectResponse> s3InputStream = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(dto.getBucketName())
                        .key(dto.getObjectKey())
                        .build()
        );

        InputStreamResource resource = new InputStreamResource(s3InputStream);
        return resource;
    }


    public void deleteMarkedFilesFromStorage(){

        List<FileMetadataDto> toDeleteList = fileMetadataService.getFileMetadataToDeleteList();

        for(FileMetadataDto dto: toDeleteList){
          removeFromStorage(dto);
        }



    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeFromStorage(FileMetadataDto fileMetadata){


        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(fileMetadata.getBucketName())
                .key(fileMetadata.getObjectKey())
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        fileMetadata.setStatus(FileStatus.DELETED);
        fileMetadataService.updateFileMetadata(fileMetadata);




    }

    private void uploadToStorage(MultipartFile file, String bucketName, String objectKey) {
        try {
            boolean bucketExists = s3Client.listBuckets().buckets().stream()
                    .anyMatch(b -> b.name().equals(bucketName));

            if (!bucketExists) {
                s3Client.createBucket(builder -> builder.bucket(bucketName));
            }

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey) // здесь нужно использовать objectKey, а не bucketName
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to S3/MinIO", e);
        }
    }


}