package com.example.demo.repository;

import com.example.demo.entity.FocusSessionEntity;
import com.example.demo.entity.SessionGoalEntity;
import com.example.demo.entity.SessionGoalEntity.GoalStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SessionGoalRepository extends JpaRepository<SessionGoalEntity, Long> {

    List<SessionGoalEntity> findByFocusSession(FocusSessionEntity focusSession);

    List<SessionGoalEntity> findByStatus(GoalStatus status);

    List<SessionGoalEntity> findByFocusSessionAndStatus(
            FocusSessionEntity focusSession,
            GoalStatus status);

    @Query("""
            SELECT COUNT(g)
            FROM SessionGoalEntity g
            WHERE g.focusSession = :session
            AND g.status = 'COMPLETED'
            """)
    long countCompletedGoals(
            @Param("session") FocusSessionEntity session);

    @Query("""
            SELECT g
            FROM SessionGoalEntity g
            WHERE g.status = 'COMPLETED'
            """)
    List<SessionGoalEntity> findCompletedGoals();

    List<SessionGoalEntity> findByStatusOrderByIdAsc(
            GoalStatus status);

    List<SessionGoalEntity> findAllByOrderByIdDesc();

}