package com.nasreen.carlog.auth;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class AwsConfig {
    private String region;
    private String bucket;

    @Autowired
    public AwsConfig(@Value("${aws.s3.region}") String region,
                     @Value("${aws.s3.bucket}") String bucket) {
        this.region = region;
        this.bucket = bucket;
    }

    public String getBucket() {
        return bucket;
    }

    @Bean
    AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
            .withCredentials(new EnvironmentVariableCredentialsProvider())
            .withRegion(region)
            .build();
    }
}
