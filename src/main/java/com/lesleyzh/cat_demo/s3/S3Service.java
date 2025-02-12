package com.lesleyzh.cat_demo.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {
    private final Logger logger = LoggerFactory.getLogger(S3Service.class);
    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    //这些都是s3文档里写好的，不需要自己写，实现了s3的读取和写入 
    // reference:
    // https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/PutObject.java
    public static class S3UploadException extends Exception {
        public S3UploadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public void uploadObject(String bucketName, String objectKey, String filePath) throws S3UploadException {
        try {
            Map<String, String> metadata = new HashMap<>();
            metadata.put("x-amz-meta-myVal", "test");
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .metadata(metadata)
                    .build();

            s3Client.putObject(putOb, RequestBody.fromFile(new File(filePath)));
            logger.info("Successfully placed {} into bucket {}", objectKey, bucketName);
        } catch (S3Exception e) {
            logger.error("Failed to upload {} to bucket {}", objectKey, bucketName, e);
            throw new S3UploadException("Failed to upload to S3", e);
        }
    }
    // reference:
    // https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/GetObjectData.java
    public String getObject(String bucketName, String objectKey, String filePath) {
        try {
            GetObjectRequest objectRequest =
                    GetObjectRequest.builder().key(objectKey).bucket(bucketName).build();

            byte[] data = s3Client.getObjectAsBytes(objectRequest).asByteArray();

            try (OutputStream os = new FileOutputStream(new File(filePath))) {
                os.write(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            logger.info("Successfully obtained bytes from an S3 object");
            return filePath;
        } catch (S3Exception e) {
            logger.error("S3Exception occurred while getting object {} from bucket {}", objectKey, bucketName, e);
            throw new RuntimeException("Failed to get " + objectKey + " from bucket " + bucketName, e);
        }
    }

    // reference:
    // https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/DeleteObjects.java
    // https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/DeleteMultiObjects.java
    public void deleteObject(String bucketName, String objectKey) {
        ObjectIdentifier toDelete = ObjectIdentifier.builder().key(objectKey).build();

        try {
            DeleteObjectsRequest dor = DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(Delete.builder().objects(toDelete).build())
                    .build();

            s3Client.deleteObjects(dor);

        } catch (S3Exception e) {
            logger.error(
                    "S3Exception while deleting object: {}", e.awsErrorDetails().errorMessage());
            throw new RuntimeException(e.awsErrorDetails().errorMessage());
        }
    }

    public void deleteObjects(String bucketName, String... objectKeys) {
        List<ObjectIdentifier> toDelete = new ArrayList<>();
        for (String objectKey : objectKeys) {
            toDelete.add(ObjectIdentifier.builder().key(objectKey).build());
        }

        try {
            DeleteObjectsRequest dor = DeleteObjectsRequest.builder()
                    .bucket(bucketName)
                    .delete(Delete.builder().objects(toDelete).build())
                    .build();

            s3Client.deleteObjects(dor);

        } catch (S3Exception e) {
            logger.error(
                    "S3Exception while deleting objects: {}",
                    e.awsErrorDetails().errorMessage());
            throw new RuntimeException(e.awsErrorDetails().errorMessage());
        }
    }

    // https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/ListObjects.java
    // https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/s3/src/main/java/com/example/s3/ListObjectsPaginated.java


    // Extract filename from S3 key
    private String extractFileNameFromKey(String key) {
        // Assume filename is the last part
        return key.substring(key.lastIndexOf('/') + 1);
    }

    // Extract metadata from S3 key
    private Map<String, String> extractObjectMetadata(String bucketName, String key) {
        try {
            HeadObjectRequest headObjectRequest =
                    HeadObjectRequest.builder().bucket(bucketName).key(key).build();
            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);
            return headObjectResponse.metadata();
        } catch (S3Exception e) {
            logger.error("Error getting metadata for object {} in bucket {}", key, bucketName, e);
            throw new RuntimeException(
                    String.format("Error getting metadata for object %s in bucket %s", key, bucketName, e));
        }
    }
}
