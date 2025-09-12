package com.georgiiHadzhiev.fileservice.config;

import com.georgiiHadzhiev.fileservice.properties.S3ClientProperties;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

public class S3ClientConfig {


    private final S3ClientProperties props;

    public S3ClientConfig(S3ClientProperties props) {
        this.props = props;
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(props.getEndpoint()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(props.getAccessKey(), props.getSecretKey())
                        )
                )
                .region(Region.EU_SOUTH_2) // MinIO не использует регион реально
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // обязательно для MinIO
                        .build())
                .build();
    }
}
