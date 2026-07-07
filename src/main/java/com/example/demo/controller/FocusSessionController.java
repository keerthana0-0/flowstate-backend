package com.example.demo.controller;

import com.example.demo.entity.FocusSessionEntity;
import com.example.demo.entity.FocusSessionEntity.SessionStatus;
import com.example.demo.service.FocusSessionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/focus-sessions")
public class FocusSessionController {

    @Autowired
    private FocusSessionService focusSessionService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<FocusSessionEntity> createSession(
            @PathVariable Long userId,
            @Valid @RequestBody FocusSessionEntity session) {

        FocusSessionEntity savedSession =
                focusSessionService.createSession(userId, session);

        return new ResponseEntity<>(savedSession, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FocusSessionEntity>> getAllSessions() {

        return ResponseEntity.ok(focusSessionService.getAllSessions());
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<FocusSessionEntity> getSessionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                focusSessionService.getSessionById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FocusSessionEntity>> getSessionsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                focusSessionService.getSessionsByUser(userId));
    }

    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<FocusSessionEntity>> getSessionsByStatus(
            @PathVariable SessionStatus status) {

        return ResponseEntity.ok(
                focusSessionService.getSessionsByStatus(status));
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<FocusSessionEntity> updateSession(
            @PathVariable Long id,
            @Valid @RequestBody FocusSessionEntity session) {

        return ResponseEntity.ok(
                focusSessionService.updateSession(id, session));
    }

    
    @PutMapping("/{id}/complete")
    public ResponseEntity<FocusSessionEntity> completeSession(
            @PathVariable Long id,
            @RequestParam Integer focusScore) {

        return ResponseEntity.ok(
                focusSessionService.completeSession(id, focusScore));
    }

   
    @PutMapping("/{id}/score")
    public ResponseEntity<FocusSessionEntity> updateFocusScore(
            @PathVariable Long id,
            @RequestParam Integer score) {

        return ResponseEntity.ok(
                focusSessionService.updateFocusScore(id, score));
    }

   
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countCompletedSessions(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                focusSessionService.countCompletedSessions(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSession(
            @PathVariable Long id) {

        focusSessionService.deleteSession(id);

        return ResponseEntity.ok(
                "Focus Session deleted successfully.");
    }

}