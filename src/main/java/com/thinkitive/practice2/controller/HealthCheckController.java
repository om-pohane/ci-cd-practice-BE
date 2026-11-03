package com.thinkitive.practice2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class HealthCheckController {

    @GetMapping(value = {"/api/health", "/api/diwali/ping", "/api/status"})
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("festival", "Diwali 2026");
        status.put("app", "Ghar Ki Safai: Survival Mode 🧹✨");
        status.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(status);
    }

    /**
     * Backward compatibility endpoint for legacy CI/CD deployment healthchecks
     * that query GET /api/student. Returns HTTP 200 with an empty list.
     */
    @GetMapping("/api/student")
    public ResponseEntity<List<Object>> studentCompatibility() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
