package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "distraction_logs")
public class DistractionLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Distraction type is required")
    @Column(nullable = false)
    private String distractionType;

    @Column(length = 500)
    private String description;

    @NotNull(message = "Distraction time is required")
    @Column(nullable = false)
    private LocalDateTime distractionTime;

    @NotNull(message = "Duration is required")
    @Column(nullable = false)
    private Integer durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "focus_session_id", nullable = false)
    private FocusSessionEntity focusSession;

    public DistractionLogEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistractionType() {
        return distractionType;
    }

    public void setDistractionType(String distractionType) {
        this.distractionType = distractionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDistractionTime() {
        return distractionTime;
    }

    public void setDistractionTime(LocalDateTime distractionTime) {
        this.distractionTime = distractionTime;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public FocusSessionEntity getFocusSession() {
        return focusSession;
    }

    public void setFocusSession(FocusSessionEntity focusSession) {
        this.focusSession = focusSession;
    }
}