package com.sparta.miniproject.S3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
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

    public String uploadObject(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
        try {
            if (!(fileName.endsWith(".bmp") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))) {
                throw new IllegalArgumentException("bmp, jpg, jpeg, png 형식의 이미지 파일이 필요합니다.");
            }
            s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드가 실패하였습니다.");
        }
        return s3Client.getUrl(bucket, fileName).toString();
    }


    public void deleteObject(String sourceKey) {
        s3Client.deleteObject(bucket, sourceKey);
    }
}
