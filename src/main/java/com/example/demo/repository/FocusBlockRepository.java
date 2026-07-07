package com.example.demo.repository;

import com.example.demo.entity.FocusBlockEntity;
import com.example.demo.entity.FocusBlockEntity.BlockStatus;
import com.example.demo.entity.FocusSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FocusBlockRepository extends JpaRepository<FocusBlockEntity, Long> {

    
    List<FocusBlockEntity> findByFocusSession(FocusSessionEntity focusSession);

    List<FocusBlockEntity> findByStatus(BlockStatus status);

    List<FocusBlockEntity> findByFocusSessionAndStatus(
            FocusSessionEntity focusSession,
            BlockStatus status);

    List<FocusBlockEntity> findByStartTimeBetween(
            LocalDateTime startTime,
            LocalDateTime endTime);

    
    long countByFocusSession(FocusSessionEntity focusSession);

    @Query("""
            SELECT COUNT(fb)
            FROM FocusBlockEntity fb
            WHERE fb.focusSession = :session
            AND fb.status = 'COMPLETED'
            """)
    long countCompletedBlocks(
            @Param("session") FocusSessionEntity session);

    List<FocusBlockEntity> findAllByOrderByStartTimeDesc();

}