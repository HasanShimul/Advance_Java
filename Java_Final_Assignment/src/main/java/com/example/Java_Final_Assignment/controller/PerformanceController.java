package com.example.Java_Final_Assignment.controller;

import com.example.Java_Final_Assignment.dto.PerformanceRequestDTO;
import com.example.Java_Final_Assignment.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    @Autowired
    private PerformanceService service;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate(@RequestBody PerformanceRequestDTO req) {
        return ResponseEntity.ok(service.calculate(req));
    }
}