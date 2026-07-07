package com.example.demo.repository;

import com.example.demo.entity.FlowUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowUserRepository extends JpaRepository<FlowUserEntity, Long> {

    Optional<FlowUserEntity> findByUsername(String username);
    Optional<FlowUserEntity> findByEmail(String email); 
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<FlowUserEntity> findByRole(FlowUserEntity.UserRole role);
    
    @Query("""
            SELECT u
            FROM FlowUserEntity u
            WHERE u.isActive = true
            AND u.role = :role
            """)
    List<FlowUserEntity> findActiveByRole(
            @Param("role") FlowUserEntity.UserRole role);

    @Query("""
            SELECT COUNT(u)
            FROM FlowUserEntity u
            WHERE u.role = :role
            AND u.isActive = true
            """)
    long countActiveByRole(
            @Param("role") FlowUserEntity.UserRole role);

}