package com.example.jenkinsfiletest.api;

import com.example.jenkinsfiletest.util.UtilClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebMainController {

    @GetMapping("/")
    public ResponseEntity getMainPage() {
        UtilClass utilClass = new UtilClass();
        return ResponseEntity.ok()
                .body(String.format("Welcome to the Jenkinsfile WEB1 test page! #%s", utilClass.sum(2, 3)).getBytes());
    }

    // comment for git changes 1

}
