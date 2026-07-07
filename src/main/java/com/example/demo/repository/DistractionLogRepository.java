package com.example.demo.repository;

import com.example.demo.entity.DistractionLogEntity;
import com.example.demo.entity.FocusSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DistractionLogRepository extends JpaRepository<DistractionLogEntity, Long> {

    List<DistractionLogEntity> findByFocusSession(FocusSessionEntity focusSession);

    List<DistractionLogEntity> findByDistractionTimeBetween(
            LocalDateTime startTime,
            LocalDateTime endTime);

    List<DistractionLogEntity> findByDistractionType(String distractionType);

    List<DistractionLogEntity> findAllByOrderByDistractionTimeDesc();

    long countByFocusSession(FocusSessionEntity focusSession);

    @Query("""
            SELECT COALESCE(SUM(d.durationMinutes),0)
            FROM DistractionLogEntity d
            WHERE d.focusSession = :session
            """)
    Integer getTotalDistractionDuration(
            @Param("session") FocusSessionEntity session);

    List<DistractionLogEntity> findByDurationMinutesGreaterThan(Integer minutes);

}