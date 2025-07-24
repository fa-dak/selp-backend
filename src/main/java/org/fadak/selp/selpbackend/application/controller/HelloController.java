package org.fadak.selp.selpbackend.application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 테스트 컨트롤러
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {

        return ResponseEntity.ok("hello");
    }
}
