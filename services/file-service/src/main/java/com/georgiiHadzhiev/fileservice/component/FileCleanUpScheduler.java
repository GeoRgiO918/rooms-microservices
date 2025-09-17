package com.georgiiHadzhiev.fileservice.component;

import com.georgiiHadzhiev.fileservice.entity.FileMetadata;
import com.georgiiHadzhiev.fileservice.entity.FileStatus;
import com.georgiiHadzhiev.fileservice.repository.FileMetadataRepository;
import com.georgiiHadzhiev.fileservice.service.FileService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

@Component
public class FileCleanUpScheduler {

    private final FileService fileService;
    private static Logger log = LoggerFactory.getLogger(FileCleanUpScheduler.class);

    public FileCleanUpScheduler(FileService fileService) {
        this.fileService = fileService;
    }


    @Scheduled(fixedRate = 100000)
    private void runScheduledCleanUp(){
        log.info("ScheduledCleanUp started");
        fileService.deleteMarkedFilesFromStorage();
        log.info("ScheduledCleanUp completed");
    }
}
