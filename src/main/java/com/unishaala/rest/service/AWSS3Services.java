package com.unishaala.rest.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AWSS3Services {
    private final AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    public String uploadFileInS3(final MultipartFile filePart) {
        final String s3key = UUID.randomUUID() + "_" + filePart.getName().replace(" ", "_");
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3key, convertMultiPartFileToFile(filePart)).withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult putObjectResult = s3client.putObject(putObjectRequest);
        return endpointUrl + s3key;
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            log.error("Error converting the multi-part file to file {} ", ex.getMessage());
        }
        return file;
    }

    public Boolean deleteFilesInS3(final String key) {
        try {
            s3client.deleteObject(bucketName, key);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    public S3Object getFile(final String bucketName, final String objectKey) {
        return s3client.getObject(bucketName, objectKey);
    }
}
