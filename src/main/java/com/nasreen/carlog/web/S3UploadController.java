package com.nasreen.carlog.web;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.nasreen.carlog.auth.AwsConfig;
import com.nasreen.carlog.service.AwsSignedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AwsSignedUrlService signedUrlService;

    @Autowired
    public S3UploadController(AwsSignedUrlService signedUrlService) {
        this.signedUrlService = signedUrlService;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public Map signedUrl() {
        String objectKey = UUID.randomUUID().toString();
        URL url = signedUrlService.generateUrl(objectKey, HttpMethod.PUT);
        return Map.of("url", url, "key", objectKey);
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.GET)
    public Map signedUrl(@PathVariable String  key) {
        URL url = signedUrlService.generateUrl(key, HttpMethod.GET);
        return Map.of("url", url, "key", key);
    }

    @RequestMapping(path = "/delete/{key}", method = RequestMethod.GET)
    public Map signedUrlForDelete(@PathVariable String  key) {
        URL url = signedUrlService.generateUrl(key, HttpMethod.DELETE);
        return Map.of("url", url, "key", key);
    }
}
