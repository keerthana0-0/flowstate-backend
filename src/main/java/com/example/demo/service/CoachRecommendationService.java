package com.example.demo.service;

import com.example.demo.entity.CoachRecommendationEntity;
import com.example.demo.entity.CoachRecommendationEntity.RecommendationStatus;
import com.example.demo.entity.FlowUserEntity;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CoachRecommendationRepository;
import com.example.demo.repository.FlowUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CoachRecommendationService {

    @Autowired
    private CoachRecommendationRepository coachRecommendationRepository;

    @Autowired
    private FlowUserRepository flowUserRepository;
    public CoachRecommendationEntity createRecommendation(Long userId,
            CoachRecommendationEntity recommendation) {

        FlowUserEntity user = flowUserRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + userId));

        if (recommendation.getTitle() == null || recommendation.getTitle().trim().isEmpty()) {
            throw new BusinessValidationException("Recommendation title cannot be empty.");
        }

        recommendation.setFlowUser(user);

        return coachRecommendationRepository.save(recommendation);
    }

    public List<CoachRecommendationEntity> getAllRecommendations() {
        return coachRecommendationRepository.findAll();
    }

    public CoachRecommendationEntity getRecommendationById(Long id) {

        return coachRecommendationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found with id : " + id));
    }

    public List<CoachRecommendationEntity> getRecommendationsByUser(Long userId) {

        FlowUserEntity user = flowUserRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + userId));

        return coachRecommendationRepository.findByFlowUser(user);
    }

    public List<CoachRecommendationEntity> getRecommendationsByStatus(
            RecommendationStatus status) {

        return coachRecommendationRepository.findByStatus(status);
    }

    public CoachRecommendationEntity updateRecommendation(Long id,
            CoachRecommendationEntity updatedRecommendation) {

        CoachRecommendationEntity existingRecommendation =
                coachRecommendationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found with id : " + id));

        existingRecommendation.setTitle(updatedRecommendation.getTitle());
        existingRecommendation.setMessage(updatedRecommendation.getMessage());
        existingRecommendation.setStatus(updatedRecommendation.getStatus());

        return coachRecommendationRepository.save(existingRecommendation);
    }

    public CoachRecommendationEntity acceptRecommendation(Long id) {

        CoachRecommendationEntity recommendation =
                coachRecommendationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found with id : " + id));

        recommendation.setStatus(RecommendationStatus.ACCEPTED);

        return coachRecommendationRepository.save(recommendation);
    }

    public CoachRecommendationEntity rejectRecommendation(Long id) {

        CoachRecommendationEntity recommendation =
                coachRecommendationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found with id : " + id));

        recommendation.setStatus(RecommendationStatus.REJECTED);

        return coachRecommendationRepository.save(recommendation);
    }

    public void deleteRecommendation(Long id) {

        CoachRecommendationEntity recommendation =
                coachRecommendationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recommendation not found with id : " + id));

        coachRecommendationRepository.delete(recommendation);
    }

    public long countRecommendations(Long userId) {

        FlowUserEntity user = flowUserRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + userId));

        return coachRecommendationRepository.countByFlowUser(user);
    }

    public long countAcceptedRecommendations(Long userId) {

        FlowUserEntity user = flowUserRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + userId));

        return coachRecommendationRepository.countAcceptedRecommendations(user);
    }

}