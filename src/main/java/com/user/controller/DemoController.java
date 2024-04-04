package com.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/v1/hello")
    public ResponseEntity<String> getData() {
        return ResponseEntity.ok("Got the Data!!");
    }
}