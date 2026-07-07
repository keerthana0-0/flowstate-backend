package com.example.demo.service;

import com.example.demo.entity.DistractionLogEntity;
import com.example.demo.entity.FocusSessionEntity;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DistractionLogRepository;
import com.example.demo.repository.FocusSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistractionLogService {

    @Autowired
    private DistractionLogRepository distractionLogRepository;

    @Autowired
    private FocusSessionRepository focusSessionRepository;

    public DistractionLogEntity createDistractionLog(Long sessionId,
                                                     DistractionLogEntity log) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Focus Session not found with id : " + sessionId));

        if (log.getDurationMinutes() <= 0) {
            throw new BusinessValidationException(
                    "Duration must be greater than zero.");
        }

        log.setFocusSession(session);

        return distractionLogRepository.save(log);
    }

    public List<DistractionLogEntity> getAllDistractionLogs() {

        return distractionLogRepository.findAll();
    }

    public DistractionLogEntity getDistractionLogById(Long id) {

        return distractionLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Distraction Log not found with id : " + id));
    }

    public List<DistractionLogEntity> getLogsBySession(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Focus Session not found with id : " + sessionId));

        return distractionLogRepository.findByFocusSession(session);
    }

    public List<DistractionLogEntity> getLogsByType(String type) {

        return distractionLogRepository.findByDistractionType(type);
    }

    public DistractionLogEntity updateDistractionLog(Long id,
                                                     DistractionLogEntity updatedLog) {

        DistractionLogEntity existingLog = distractionLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Distraction Log not found with id : " + id));

        if (updatedLog.getDurationMinutes() <= 0) {
            throw new BusinessValidationException(
                    "Duration must be greater than zero.");
        }

        existingLog.setDistractionType(updatedLog.getDistractionType());
        existingLog.setDescription(updatedLog.getDescription());
        existingLog.setDistractionTime(updatedLog.getDistractionTime());
        existingLog.setDurationMinutes(updatedLog.getDurationMinutes());

        return distractionLogRepository.save(existingLog);
    }

    public void deleteDistractionLog(Long id) {

        DistractionLogEntity log = distractionLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Distraction Log not found with id : " + id));

        distractionLogRepository.delete(log);
    }

    public long countDistractionLogs(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Focus Session not found with id : " + sessionId));

        return distractionLogRepository.countByFocusSession(session);
    }

    public Integer getTotalDistractionDuration(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Focus Session not found with id : " + sessionId));

        return distractionLogRepository.getTotalDistractionDuration(session);
    }

}