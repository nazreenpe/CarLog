package com.nasreen.carlog.web;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.nasreen.carlog.auth.AwsConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/s3upload",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class S3UploadController {
    private final AwsConfig awsConfig;
    private final AmazonS3 amazonS3;

    public S3UploadController(AwsConfig awsConfig, AmazonS3 amazonS3) {
        this.awsConfig = awsConfig;
        this.amazonS3 = amazonS3;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public Map signedUrl() {
        String objectKey = UUID.randomUUID().toString();
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(awsConfig.getBucket(), objectKey)
            .withMethod(HttpMethod.PUT)
            .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(request);
        return Map.of("url", url, "key", objectKey);
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.GET)
    public Map signedUrl(@PathVariable String  key) {
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(awsConfig.getBucket(), key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(request);
        return Map.of("url", url, "key", key);
    }

    @RequestMapping(path = "/delete/{key}", method = RequestMethod.GET)
    public Map signedUrlForDelete(@PathVariable String  key) {
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.MINUTES));

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(awsConfig.getBucket(), key)
                .withMethod(HttpMethod.DELETE)
                .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(request);
        return Map.of("url", url, "key", key);
    }
}
