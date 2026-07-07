package com.example.demo.controller;

import com.example.demo.entity.SessionGoalEntity;
import com.example.demo.entity.SessionGoalEntity.GoalStatus;
import com.example.demo.service.SessionGoalService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session-goals")
public class SessionGoalController {

    @Autowired
    private SessionGoalService sessionGoalService;

    @PostMapping("/session/{sessionId}")
    public ResponseEntity<SessionGoalEntity> createGoal(
            @PathVariable Long sessionId,
            @Valid @RequestBody SessionGoalEntity goal) {

        SessionGoalEntity savedGoal =
                sessionGoalService.createGoal(sessionId, goal);

        return new ResponseEntity<>(savedGoal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SessionGoalEntity>> getAllGoals() {

        return ResponseEntity.ok(sessionGoalService.getAllGoals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionGoalEntity> getGoalById(
            @PathVariable Long id) {

        return ResponseEntity.ok(sessionGoalService.getGoalById(id));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<SessionGoalEntity>> getGoalsBySession(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                sessionGoalService.getGoalsBySession(sessionId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SessionGoalEntity>> getGoalsByStatus(
            @PathVariable GoalStatus status) {

        return ResponseEntity.ok(
                sessionGoalService.getGoalsByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionGoalEntity> updateGoal(
            @PathVariable Long id,
            @Valid @RequestBody SessionGoalEntity goal) {

        return ResponseEntity.ok(
                sessionGoalService.updateGoal(id, goal));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<SessionGoalEntity> completeGoal(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sessionGoalService.completeGoal(id));
    }

    @GetMapping("/count/{sessionId}")
    public ResponseEntity<Long> countCompletedGoals(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                sessionGoalService.countCompletedGoals(sessionId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoal(
            @PathVariable Long id) {

        sessionGoalService.deleteGoal(id);

        return ResponseEntity.ok("Session Goal deleted successfully.");
    }
}