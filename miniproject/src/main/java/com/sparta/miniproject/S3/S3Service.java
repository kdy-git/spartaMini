package com.sparta.miniproject.S3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.sparta.miniproject.exception.BusinessException;
import com.sparta.miniproject.exception.ErrorCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@NoArgsConstructor
public class S3Service {

    private AmazonS3 s3Client;
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String uploadImage(MultipartFile imageFile) {
            String fileName = UUID.randomUUID() + "-" + Objects.requireNonNull(imageFile.getOriginalFilename()).toLowerCase();
            try {
                if (!(fileName.endsWith(".bmp") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))) {
                    throw new BusinessException(ErrorCode.INVALID_IMAGE_FILE_EXTENSION);
                }
                s3Client.putObject(new PutObjectRequest(bucket, fileName, imageFile.getInputStream(), null)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.S3_UPLOAD_FAILED);
            }
            return s3Client.getUrl(bucket, fileName).toString();
    }

    public List<DeleteObjectsRequest.KeyVersion> getImageKeys() {
        ListObjectsV2Result result = s3Client.listObjectsV2(bucket);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        List<DeleteObjectsRequest.KeyVersion> imageNameList = new CopyOnWriteArrayList<>();
        for (S3ObjectSummary os : objects) {
            imageNameList.add(new DeleteObjectsRequest.KeyVersion(os.getKey()));
        }
        return imageNameList;
    }

    public void deleteObjects(List<DeleteObjectsRequest.KeyVersion> object_keys) {
        DeleteObjectsRequest dor = new DeleteObjectsRequest(bucket)
                .withKeys(object_keys);
        try {
            s3Client.deleteObjects(dor);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }
}
