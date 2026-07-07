package com.example.demo.service;

import com.example.demo.entity.FlowUserEntity;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FlowUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtUtil;
import com.example.demo.dto.LoginRequestDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlowUserService {

    @Autowired
    private FlowUserRepository flowUserRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public FlowUserEntity register(FlowUserEntity user) {

    return createUser(user);

}
public String login(LoginRequestDto loginRequest) {

    FlowUserEntity user = flowUserRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Invalid username or password"));

    if (!passwordEncoder.matches(
            loginRequest.getPassword(),
            user.getPasswordHash())) {

        throw new BusinessValidationException("Invalid username or password");
    }

    updateLastLogin(user.getId());

    return jwtUtil.generateToken(user.getUsername(), user.getRole().name());

}

    public FlowUserEntity createUser(FlowUserEntity user) {

    
        if (flowUserRepository.existsByUsername(user.getUsername())) 
        {
            throw new BusinessValidationException("Username already exists.");
        }

        if (flowUserRepository.existsByEmail(user.getEmail())) 
        {
            throw new BusinessValidationException("Email already exists.");
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

    
        if (user.getIsActive() == null) 
        {
            user.setIsActive(true);
        }

        user.setLastLoginAt(null);

        return flowUserRepository.save(user);
    }

    public FlowUserEntity getUserById(Long id) {

        return flowUserRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException( "FlowUser not found with id : " + id));
    }

    public FlowUserEntity getUserByUsername(String username) {

        return flowUserRepository.findByUsername(username).orElseThrow(() ->new ResourceNotFoundException("FlowUser not found : " + username));
    }

 
public List<FlowUserEntity> getAllUsers() {

    return flowUserRepository.findAll();
}


public List<FlowUserEntity> getUsersByRole(FlowUserEntity.UserRole role) {

    List<FlowUserEntity> users = flowUserRepository.findByRole(role);

    if (users.isEmpty()) {
        throw new ResourceNotFoundException("No users found with role : " + role);
    }

    return users;
}

public List<FlowUserEntity> getActiveUsersByRole(FlowUserEntity.UserRole role) {

    List<FlowUserEntity> users =
            flowUserRepository.findActiveByRole(role);

    if (users.isEmpty()) {
        throw new ResourceNotFoundException(
                "No active users found with role : " + role);
    }

    return users;
}

public long countActiveUsersByRole(FlowUserEntity.UserRole role) {

    return flowUserRepository.countActiveByRole(role);
}


public void updateLastLogin(Long userId) {

    FlowUserEntity user = flowUserRepository.findById(userId)
            .orElseThrow(() ->new ResourceNotFoundException("FlowUser not found with id : " + userId));

    user.setLastLoginAt(LocalDateTime.now());

    flowUserRepository.save(user);
}


public FlowUserEntity updateUser(Long id, FlowUserEntity updatedUser) {

    FlowUserEntity existingUser = flowUserRepository.findById(id)
            .orElseThrow(() ->new ResourceNotFoundException("FlowUser not found with id : " + id));

    
    if (!existingUser.getUsername().equals(updatedUser.getUsername())
            && flowUserRepository.existsByUsername(updatedUser.getUsername())) {

        throw new BusinessValidationException("Username already exists.");
    }

    
    if (!existingUser.getEmail().equals(updatedUser.getEmail())
            && flowUserRepository.existsByEmail(updatedUser.getEmail())) {

        throw new BusinessValidationException(
                "Email already exists.");
    }

    existingUser.setUsername(updatedUser.getUsername());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setFullName(updatedUser.getFullName());
    existingUser.setRole(updatedUser.getRole());
    existingUser.setIsActive(updatedUser.getIsActive());

    if (updatedUser.getPasswordHash() != null
            && !updatedUser.getPasswordHash().isBlank()) {

        existingUser.setPasswordHash(
                passwordEncoder.encode(updatedUser.getPasswordHash()));
    }

    return flowUserRepository.save(existingUser);
}

public void deleteUser(Long id) {

    FlowUserEntity user = flowUserRepository.findById(id)
            .orElseThrow(() ->new ResourceNotFoundException("FlowUser not found with id : " + id));

    if (!user.getIsActive()) {
        throw new BusinessValidationException("User is already inactive.");
    }

    user.setIsActive(false);

    flowUserRepository.save(user);
}
}