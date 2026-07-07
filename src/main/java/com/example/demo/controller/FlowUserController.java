package com.example.demo.controller;

import com.example.demo.entity.FlowUserEntity;
import com.example.demo.service.FlowUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class FlowUserController {

    @Autowired
    private FlowUserService flowUserService;

    @PostMapping("/register")
       public ResponseEntity<FlowUserEntity> register(
        @Valid @RequestBody FlowUserEntity user) {

    FlowUserEntity savedUser = flowUserService.register(user);
    return new ResponseEntity<>(savedUser,HttpStatus.CREATED);

}

@PostMapping("/login")
public ResponseEntity<LoginResponseDto> login(
        @RequestBody LoginRequestDto loginRequest) {

    String token =flowUserService.login(loginRequest);
    return ResponseEntity.ok(new LoginResponseDto(token));

}

   
    @PostMapping
    public ResponseEntity<FlowUserEntity> createUser(
            @Valid @RequestBody FlowUserEntity user) {

        FlowUserEntity savedUser = flowUserService.createUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

   
    @GetMapping
    public ResponseEntity<List<FlowUserEntity>> getAllUsers() {

        return ResponseEntity.ok(flowUserService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowUserEntity> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(flowUserService.getUserById(id));
    }

   
    @GetMapping("/username/{username}")
    public ResponseEntity<FlowUserEntity> getUserByUsername(
            @PathVariable String username) {

        return ResponseEntity.ok(
                flowUserService.getUserByUsername(username));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<FlowUserEntity>> getUsersByRole(
            @PathVariable FlowUserEntity.UserRole role) {

        return ResponseEntity.ok(
                flowUserService.getUsersByRole(role));
    }

    
    @GetMapping("/active/{role}")
    public ResponseEntity<List<FlowUserEntity>> getActiveUsersByRole(
            @PathVariable FlowUserEntity.UserRole role) {

        return ResponseEntity.ok(
                flowUserService.getActiveUsersByRole(role));
    }

    @GetMapping("/count/{role}")
    public ResponseEntity<Long> countActiveUsersByRole(
            @PathVariable FlowUserEntity.UserRole role) {

        return ResponseEntity.ok(
                flowUserService.countActiveUsersByRole(role));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<FlowUserEntity> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody FlowUserEntity user) {

        return ResponseEntity.ok(
                flowUserService.updateUser(id, user));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        flowUserService.deleteUser(id);

        return ResponseEntity.ok("FlowUser deleted successfully.");
    }

}