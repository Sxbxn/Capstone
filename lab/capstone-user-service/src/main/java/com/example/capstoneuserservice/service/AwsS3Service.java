package com.example.capstoneuserservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.capstoneuserservice.config.AwsS3Config;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3Client;

    public String uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
        }

        String url = getS3(bucket, fileName);

        return url;
    }

    public String getS3(String bucket, String fileName) {
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteImage(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

//    public String upload(String url) throws IOException {
//        String key = createFileNameUrl(url);
//        URL requestUrl = new URL(url);
//
//        if (ImageIO.read(requestUrl) == null) {
//            throw new IllegalArgumentException("Invalid File format : NON-IMAGE FILE");
//        }
//
//        File uploadFile = new File("temp.jpg");
//
//        if (uploadFile.createNewFile()) {
//            FileUtils.copyURLToFile(requestUrl, uploadFile);
//        } else {
//            throw new IOException("Could not create new File, internal server error");
//        }
//
//        PutObjectRequest request = new PutObjectRequest(AwsS3Config.s3Bucket, key, uploadFile)
//                .withCannedAcl(CannedAccessControlList.PublicRead);
//
//        amazonS3Client.putObject(request);
//
//        uploadFile.delete();
//
//        return amazonS3Client.getUrl(AwsS3Config.s3Bucket, key).toString();
//    }

//    private String createFileNameUrl(String url) {
//        return UUID.randomUUID().toString().concat(url);
//    }
//
//    private String createFileName(String fileName) {
//        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
//    }
//
//    private String getFileExtension(String fileName) {
//        try {
//            return fileName.substring(fileName.lastIndexOf("."));
//        } catch (StringIndexOutOfBoundsException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
//        }
//    }
}
