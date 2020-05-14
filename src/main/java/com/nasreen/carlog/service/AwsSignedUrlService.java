package com.nasreen.carlog.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.nasreen.carlog.auth.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class AwsSignedUrlService {
    private final AwsConfig awsConfig;
    private final AmazonS3 amazonS3;

    @Autowired
    public AwsSignedUrlService(AwsConfig awsConfig, AmazonS3 amazonS3) {
        this.awsConfig = awsConfig;
        this.amazonS3 = amazonS3;
    }

    public URL generateUrl(String objectKey, HttpMethod put) {
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(awsConfig.getBucket(), objectKey)
            .withMethod(put)
            .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(request);
    }
}
