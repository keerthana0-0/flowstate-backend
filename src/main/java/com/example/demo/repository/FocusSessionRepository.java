package com.example.demo.repository;

import com.example.demo.entity.FlowUserEntity;
import com.example.demo.entity.FocusSessionEntity;
import com.example.demo.entity.FocusSessionEntity.SessionStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FocusSessionRepository extends JpaRepository<FocusSessionEntity, Long> {

 
    List<FocusSessionEntity> findByFlowUser(FlowUserEntity flowUser);

   
    List<FocusSessionEntity> findByStatus(SessionStatus status);

    List<FocusSessionEntity> findByFlowUserAndStatus(
            FlowUserEntity flowUser,
            SessionStatus status);

  
    List<FocusSessionEntity> findByStartTimeBetween(
            LocalDateTime start,
            LocalDateTime end);

    @Query("""
            SELECT fs
            FROM FocusSessionEntity fs
            WHERE fs.flowUser = :user
            AND fs.status = 'COMPLETED'
            """)
    List<FocusSessionEntity> findCompletedSessions(
            @Param("user") FlowUserEntity user);

   
    @Query("""
            SELECT COUNT(fs)
            FROM FocusSessionEntity fs
            WHERE fs.flowUser = :user
            AND fs.status = 'COMPLETED'
            """)
    long countCompletedSessions(
            @Param("user") FlowUserEntity user);

    List<FocusSessionEntity> findByFocusScoreGreaterThanEqual(
            Integer score);

   
    List<FocusSessionEntity> findAllByOrderByStartTimeDesc();

}