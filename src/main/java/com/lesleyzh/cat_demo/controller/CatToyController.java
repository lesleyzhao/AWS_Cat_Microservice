package com.lesleyzh.cat_demo.controller;


import com.lesleyzh.cat_demo.dto.CatLocation;
import com.lesleyzh.cat_demo.kafka.StreamProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cat-toy")
public class CatToyController {
    private final StreamProducerService<CatLocation> producerService;

    public CatToyController(StreamProducerService<CatLocation> producerService) {
        this.producerService = producerService;
    }


    //接受进来的client request，放入kafka里
    @PostMapping("/collect-location")
    public ResponseEntity<String> collectLocation(@RequestBody CatLocation catLocation){
        //validate cat location
        if(catLocation.locationX() < 0 || catLocation.locationY() < 0){
            return ResponseEntity.badRequest().body("Invalid location");
        }

        producerService.produceMessage(catLocation);
        return ResponseEntity.ok("Location collected");
    }
}
