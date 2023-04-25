package com.eureka.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AyushmanController {
    private static final Logger LOG = LoggerFactory.
            getLogger(AyushmanController.class);
    @Value("${ayushamn.ravan}")
    private String numberOfProcessors;

    @GetMapping("/tutorials/published")
    public ResponseEntity<?> findByPublished() {
        LOG.info("Started....");
        Map<String,Object> map = new HashMap<>();
        map.put("message",numberOfProcessors);
        LOG.info("End .......");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
