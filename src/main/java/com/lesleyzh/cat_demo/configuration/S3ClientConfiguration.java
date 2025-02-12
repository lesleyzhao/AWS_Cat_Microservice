//package com.lesleyzh.cat_demo.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
//import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.s3.S3Client;
//
//@Configuration
//public class S3ClientConfiguration {
//    @Bean
//    public S3Client s3Client(){
//        return S3Client.builder()
//                .credentialsProvider(StaticCredentialsProvider.create(
//                        AwsBasicCredentials.create("fakeMyKeyId", "fakeSecretAccessKey")
//                )).region(Region.US_EAST_1).build();
//    }
//}
