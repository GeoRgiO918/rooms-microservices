package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.FileStatus;
import com.georgiiHadzhiev.fileservice.repository.FileMetadataRepository;
import com.georgiiHadzhiev.fileservice.service.FileService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

@Component
public class FileCleanUpScheduler {

    private final FileService fileService;

    public FileCleanUpScheduler(FileService fileService) {
        this.fileService = fileService;
    }


    @Scheduled(fixedRate = 100000)
    private void runScheduledCleanUp(){
        fileService.deleteMarkedFilesFromStorage();
    }
}
