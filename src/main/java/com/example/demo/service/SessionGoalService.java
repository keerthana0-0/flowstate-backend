package com.example.demo.service;

import com.example.demo.entity.FocusSessionEntity;
import com.example.demo.entity.SessionGoalEntity;
import com.example.demo.entity.SessionGoalEntity.GoalStatus;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FocusSessionRepository;
import com.example.demo.repository.SessionGoalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionGoalService {

    @Autowired
    private SessionGoalRepository sessionGoalRepository;

    @Autowired
    private FocusSessionRepository focusSessionRepository;

    public SessionGoalEntity createGoal(Long sessionId, SessionGoalEntity goal) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Focus Session not found with id : " + sessionId));

        if (goal.getTargetDuration() <= 0) {
            throw new BusinessValidationException(
                    "Target duration must be greater than zero.");
        }

        goal.setFocusSession(session);

        return sessionGoalRepository.save(goal);
    }

    public List<SessionGoalEntity> getAllGoals() {
        return sessionGoalRepository.findAll();
    }

    public SessionGoalEntity getGoalById(Long id) {

        return sessionGoalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Goal not found with id : " + id));
    }

    public List<SessionGoalEntity> getGoalsBySession(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Focus Session not found with id : " + sessionId));

        return sessionGoalRepository.findByFocusSession(session);
    }

    public List<SessionGoalEntity> getGoalsByStatus(GoalStatus status) {

        return sessionGoalRepository.findByStatus(status);
    }

    public SessionGoalEntity updateGoal(Long id, SessionGoalEntity updatedGoal) {

        SessionGoalEntity existingGoal = sessionGoalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Goal not found with id : " + id));

        if (updatedGoal.getTargetDuration() <= 0) {
            throw new BusinessValidationException(
                    "Target duration must be greater than zero.");
        }

        existingGoal.setGoalTitle(updatedGoal.getGoalTitle());
        existingGoal.setDescription(updatedGoal.getDescription());
        existingGoal.setStatus(updatedGoal.getStatus());
        existingGoal.setTargetDuration(updatedGoal.getTargetDuration());

        return sessionGoalRepository.save(existingGoal);
    }

    public SessionGoalEntity completeGoal(Long id) {

        SessionGoalEntity goal = sessionGoalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Goal not found with id : " + id));

        goal.setStatus(GoalStatus.COMPLETED);

        return sessionGoalRepository.save(goal);
    }

    public void deleteGoal(Long id) {

        SessionGoalEntity goal = sessionGoalRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Goal not found with id : " + id));

        sessionGoalRepository.delete(goal);
    }

    public long countCompletedGoals(Long sessionId) {

        FocusSessionEntity session = focusSessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Focus Session not found with id : " + sessionId));

        return sessionGoalRepository.countCompletedGoals(session);
    }

}
