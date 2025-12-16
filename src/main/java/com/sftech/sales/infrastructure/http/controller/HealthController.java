package com.sftech.sales.infrastructure.http.controller;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/keep-alive")
    public ResponseEntity<Map<String, Object>> keepAlive() {
        return ResponseEntity.ok(
                Map.of(
                        "status", "ok",
                        "timestamp", Instant.now().toString()));
    }
}

