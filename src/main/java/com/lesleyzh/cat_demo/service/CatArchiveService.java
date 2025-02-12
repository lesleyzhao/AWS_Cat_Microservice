package com.lesleyzh.cat_demo.service;

import com.lesleyzh.cat_demo.dto.ImmutableQueueMessageDto;
import com.lesleyzh.cat_demo.dto.QueueMessageDto;
import com.lesleyzh.cat_demo.s3.S3Service;
import com.lesleyzh.cat_demo.sqs.SqsProducer;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


//archive负责从s3下载文件，切割文件放到messagequeue producer里，再把切割完的文件传回s3
@Service
public class CatArchiveService {
    private final Logger logger = Logger.getLogger(CatArchiveService.class.getName());

    private final S3Service s3Service;
    private final SqsProducer sqsProducer;

    public CatArchiveService(S3Service s3Service, SqsProducer sqsProducer) {
        this.s3Service = s3Service;
        this.sqsProducer = sqsProducer;
    }

    public void processCatArchive(String filePath) throws S3Service.S3UploadException {
        String bucketName = "demo-bucket";
        String objectKey = String.format("cat-archive-%s", UUID.randomUUID());
        downloadCatArchiveFromS3(bucketName, objectKey, filePath);
        List<String> chunkedFilePaths = chunkFileAndUploadToS3(filePath, bucketName, objectKey);
        queueJobs(chunkedFilePaths);
    }

    public void downloadCatArchiveFromS3(String bucketName, String objectKey, String filePath){
        //download the cat archive from s3
        //return the file
        s3Service.getObject(bucketName, objectKey, filePath);
    }

    public List<String> chunkFileAndUploadToS3(String filePath, String bucketName, String objectKey)
        throws S3Service.S3UploadException {
            s3Service.uploadObject(bucketName, objectKey, filePath);
            return new ArrayList<>();
    }
    public void queueJobs(List<String> chunkedFilePaths){
        //chunk the file into smaller files
        //send each chunk to the queue
        for(String chunkedFilePath : chunkedFilePaths){
            QueueMessageDto message = ImmutableQueueMessageDto.builder()
                    .message(chunkedFilePath)
                    .timestamp(System.currentTimeMillis())
                    .build();
            sqsProducer.send(message);
        }
    }
}
