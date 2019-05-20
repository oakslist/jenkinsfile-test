package com.example.jenkinsfiletest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public ResponseEntity getMainPage() {
        return ResponseEntity.ok()
                .body("Welcome to the Jenkinsfile test page!".getBytes());
    }

}
