package com.gateway.ecom.controller;

import jakarta.ws.rs.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class FallBackController {

    @GetMapping("/fallback/products")
    public ResponseEntity<List<String>> productsFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Product service is not available now!!"));
    }

    @GetMapping("/fallback/users")
    public ResponseEntity<List<String>> usersFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("User service is not available now!!"));
    }

    @GetMapping("/fallback/orders")
    public ResponseEntity<List<String>> ordersFallback(){
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Orders service is not available now!!"));
    }
}
