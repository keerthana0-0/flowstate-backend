package com.example.demo.controller;

import com.example.demo.entity.FocusBlockEntity;
import com.example.demo.entity.FocusBlockEntity.BlockStatus;
import com.example.demo.service.FocusBlockService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/focus-blocks")
public class FocusBlockController {

    @Autowired
    private FocusBlockService focusBlockService;

    
    @PostMapping("/session/{sessionId}")
    public ResponseEntity<FocusBlockEntity> createFocusBlock(
            @PathVariable Long sessionId,
            @Valid @RequestBody FocusBlockEntity block) {

        FocusBlockEntity savedBlock =
                focusBlockService.createFocusBlock(sessionId, block);

        return new ResponseEntity<>(savedBlock, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FocusBlockEntity>> getAllFocusBlocks() {

        return ResponseEntity.ok(
                focusBlockService.getAllFocusBlocks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FocusBlockEntity> getFocusBlockById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                focusBlockService.getFocusBlockById(id));
    }
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<FocusBlockEntity>> getBlocksBySession(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                focusBlockService.getBlocksBySession(sessionId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FocusBlockEntity>> getBlocksByStatus(
            @PathVariable BlockStatus status) {

        return ResponseEntity.ok(
                focusBlockService.getBlocksByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FocusBlockEntity> updateFocusBlock(
            @PathVariable Long id,
            @Valid @RequestBody FocusBlockEntity block) {

        return ResponseEntity.ok(
                focusBlockService.updateFocusBlock(id, block));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<FocusBlockEntity> completeFocusBlock(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                focusBlockService.completeFocusBlock(id));
    }

    @GetMapping("/count/{sessionId}")
    public ResponseEntity<Long> countBlocks(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                focusBlockService.countBlocks(sessionId));
    }

    @GetMapping("/completed-count/{sessionId}")
    public ResponseEntity<Long> countCompletedBlocks(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                focusBlockService.countCompletedBlocks(sessionId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFocusBlock(
            @PathVariable Long id) {

        focusBlockService.deleteFocusBlock(id);

        return ResponseEntity.ok("Focus Block deleted successfully.");
    }
}