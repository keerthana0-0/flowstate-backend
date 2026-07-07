package com.example.demo.service;

import com.example.demo.entity.FlowUserEntity;
import com.example.demo.entity.FocusSessionEntity;
import com.example.demo.entity.FocusSessionEntity.SessionStatus;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FlowUserRepository;
import com.example.demo.repository.FocusSessionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FocusSessionService {

    @Autowired
    private FocusSessionRepository focusSessionRepository;

    @Autowired
    private FlowUserRepository flowUserRepository;

    
    public FocusSessionEntity createSession(Long userId,
                                            FocusSessionEntity session) {

        FlowUserEntity user = flowUserRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + userId));

        if (session.getStartTime().isAfter(session.getEndTime())) {
            throw new BusinessValidationException(
                    "Start time cannot be after end time.");
        }

        session.setFlowUser(user);

        return focusSessionRepository.save(session);
    }

    
    public List<FocusSessionEntity> getAllSessions() {

        return focusSessionRepository.findAll();
    }

    public FocusSessionEntity getSessionById(Long id) {

        return focusSessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Focus Session not found with id : " + id));
    }

    public List<FocusSessionEntity> getSessionsByUser(Long userId) {

        FlowUserEntity user = flowUserRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id : " + userId));

        return focusSessionRepository.findByFlowUser(user);
    }

    public List<FocusSessionEntity> getSessionsByStatus(SessionStatus status) {

        return focusSessionRepository.findByStatus(status);
    }



public FocusSessionEntity updateSession(Long id, FocusSessionEntity updatedSession) {

    FocusSessionEntity existingSession = focusSessionRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Focus Session not found with id : " + id));

    if (updatedSession.getStartTime().isAfter(updatedSession.getEndTime())) {
        throw new BusinessValidationException(
                "Start time cannot be after end time.");
    }

    existingSession.setSessionName(updatedSession.getSessionName());
    existingSession.setStartTime(updatedSession.getStartTime());
    existingSession.setEndTime(updatedSession.getEndTime());
    existingSession.setStatus(updatedSession.getStatus());
    existingSession.setNotes(updatedSession.getNotes());
    existingSession.setFocusScore(updatedSession.getFocusScore());

    return focusSessionRepository.save(existingSession);

}

public FocusSessionEntity completeSession(Long id, Integer focusScore) {

    FocusSessionEntity session = focusSessionRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Focus Session not found with id : " + id));

    session.setStatus(SessionStatus.COMPLETED);
    session.setFocusScore(focusScore);

    return focusSessionRepository.save(session);
}



public FocusSessionEntity updateFocusScore(Long id, Integer score) {

    FocusSessionEntity session = focusSessionRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Focus Session not found with id : " + id));

    if (score < 0 || score > 100) {
        throw new BusinessValidationException(
                "Focus score must be between 0 and 100.");
    }

    session.setFocusScore(score);

    return focusSessionRepository.save(session);
}


public void deleteSession(Long id) {

    FocusSessionEntity session = focusSessionRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Focus Session not found with id : " + id));

    focusSessionRepository.delete(session);
}



public long countCompletedSessions(Long userId) {

    FlowUserEntity user = flowUserRepository.findById(userId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "User not found with id : " + userId));

    return focusSessionRepository.countCompletedSessions(user);
}
}