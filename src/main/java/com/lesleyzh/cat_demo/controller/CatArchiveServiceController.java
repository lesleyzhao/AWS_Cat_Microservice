package com.lesleyzh.cat_demo.controller;


import com.lesleyzh.cat_demo.s3.S3Service;
import com.lesleyzh.cat_demo.service.CatArchiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CatArchiveServiceController {
    private final Logger LOGGER = LoggerFactory.getLogger(CatArchiveServiceController.class.getName());
    private final CatArchiveService catArchiveService;

    public CatArchiveServiceController(CatArchiveService catArchiveService) {
        this.catArchiveService = catArchiveService;
    }

    //运行顺序：catArchiveServiceController -> catArchiveService 在这里切割排队上传->
    @PostMapping("/process-cat-archive")
    public ResponseEntity<String> processCatArchive(@RequestBody String filePath) {
        // download the cat archive from S3
        // chunk the file into smaller files
        // upload each chunk to S3
        // send each chunk to the queue
        LOGGER.info("Incoming request to process cat archive: {}", filePath);
        try {
            catArchiveService.processCatArchive(filePath);
        } catch (S3Service.S3UploadException e) {
            LOGGER.error("Failed to process cat archive: {}", filePath, e);
            return ResponseEntity.badRequest().body("Failed to process cat archive");
        }
        return ResponseEntity.ok("Successfully queued cat archive process job");
    }
}
