package com.example.demo.controller;

import com.example.demo.entity.CoachRecommendationEntity;
import com.example.demo.entity.CoachRecommendationEntity.RecommendationStatus;
import com.example.demo.service.CoachRecommendationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class CoachRecommendationController {

    @Autowired
    private CoachRecommendationService coachRecommendationService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<CoachRecommendationEntity> createRecommendation(
            @PathVariable Long userId,
            @Valid @RequestBody CoachRecommendationEntity recommendation) {

        CoachRecommendationEntity savedRecommendation =
                coachRecommendationService.createRecommendation(userId, recommendation);

        return new ResponseEntity<>(savedRecommendation, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CoachRecommendationEntity>> getAllRecommendations() {

        return ResponseEntity.ok(
                coachRecommendationService.getAllRecommendations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoachRecommendationEntity> getRecommendationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                coachRecommendationService.getRecommendationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CoachRecommendationEntity>> getRecommendationsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                coachRecommendationService.getRecommendationsByUser(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CoachRecommendationEntity>> getRecommendationsByStatus(
            @PathVariable RecommendationStatus status) {

        return ResponseEntity.ok(
                coachRecommendationService.getRecommendationsByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoachRecommendationEntity> updateRecommendation(
            @PathVariable Long id,
            @Valid @RequestBody CoachRecommendationEntity recommendation) {

        return ResponseEntity.ok(
                coachRecommendationService.updateRecommendation(id, recommendation));
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<CoachRecommendationEntity> acceptRecommendation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                coachRecommendationService.acceptRecommendation(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<CoachRecommendationEntity> rejectRecommendation(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                coachRecommendationService.rejectRecommendation(id));
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countRecommendations(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                coachRecommendationService.countRecommendations(userId));
    }

    @GetMapping("/accepted-count/{userId}")
    public ResponseEntity<Long> countAcceptedRecommendations(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                coachRecommendationService.countAcceptedRecommendations(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecommendation(
            @PathVariable Long id) {

        coachRecommendationService.deleteRecommendation(id);

        return ResponseEntity.ok("Coach Recommendation deleted successfully.");
    }
}