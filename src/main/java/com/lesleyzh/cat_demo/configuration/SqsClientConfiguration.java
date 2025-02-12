package com.lesleyzh.cat_demo.configuration;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class SqsClientConfiguration {
    @Bean(destroyMethod = "close")
    public SqsClient sqsClient(){
        return SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("fakeMyKeyId", "fakeSecretAccessKey")))
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    @Qualifier("demo-job-queue")
    public String demoJobQueue(){
        return "demo-job-queue";
    }

//    @Bean
//    @Qualifier("demo-job-queue1")
//    public String demoJobQueue1(){
//        return "demo-job-queue1";
//    }
}
