package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "session_goals")
public class SessionGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Goal title is required")
    @Column(nullable = false)
    private String goalTitle;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalStatus status;

    @NotNull(message = "Target duration is required")
    @Column(nullable = false)
    private Integer targetDuration;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_session_id", nullable = false)
    private FocusSessionEntity focusSession;

    public enum GoalStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }

    public SessionGoalEntity() {
    }

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = GoalStatus.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoalTitle() {
        return goalTitle;
    }

    public void setGoalTitle(String goalTitle) {
        this.goalTitle = goalTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoalStatus getStatus() {
        return status;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    public Integer getTargetDuration() {
        return targetDuration;
    }

    public void setTargetDuration(Integer targetDuration) {
        this.targetDuration = targetDuration;
    }

    public FocusSessionEntity getFocusSession() {
        return focusSession;
    }

    public void setFocusSession(FocusSessionEntity focusSession) {
        this.focusSession = focusSession;
    }
}