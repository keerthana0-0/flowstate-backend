package com.example.demo.controller;

import com.example.demo.entity.DistractionLogEntity;
import com.example.demo.service.DistractionLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/distraction-logs")
public class DistractionLogController {

    @Autowired
    private DistractionLogService distractionLogService;

    @PostMapping("/session/{sessionId}")
    public ResponseEntity<DistractionLogEntity> createDistractionLog(
            @PathVariable Long sessionId,
            @Valid @RequestBody DistractionLogEntity log) {

        DistractionLogEntity savedLog =
                distractionLogService.createDistractionLog(sessionId, log);

        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DistractionLogEntity>> getAllDistractionLogs() {

        return ResponseEntity.ok(
                distractionLogService.getAllDistractionLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistractionLogEntity> getDistractionLogById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                distractionLogService.getDistractionLogById(id));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<DistractionLogEntity>> getLogsBySession(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                distractionLogService.getLogsBySession(sessionId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<DistractionLogEntity>> getLogsByType(
            @PathVariable String type) {

        return ResponseEntity.ok(
                distractionLogService.getLogsByType(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistractionLogEntity> updateDistractionLog(
            @PathVariable Long id,
            @Valid @RequestBody DistractionLogEntity log) {

        return ResponseEntity.ok(
                distractionLogService.updateDistractionLog(id, log));
    }

    @GetMapping("/count/{sessionId}")
    public ResponseEntity<Long> countDistractionLogs(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                distractionLogService.countDistractionLogs(sessionId));
    }

    @GetMapping("/duration/{sessionId}")
    public ResponseEntity<Integer> getTotalDistractionDuration(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                distractionLogService.getTotalDistractionDuration(sessionId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDistractionLog(
            @PathVariable Long id) {

        distractionLogService.deleteDistractionLog(id);

        return ResponseEntity.ok("Distraction Log deleted successfully.");
    }
}