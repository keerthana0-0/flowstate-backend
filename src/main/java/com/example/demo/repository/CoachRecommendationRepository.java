package com.example.demo.repository;

import com.example.demo.entity.CoachRecommendationEntity;
import com.example.demo.entity.CoachRecommendationEntity.RecommendationStatus;
import com.example.demo.entity.FlowUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRecommendationRepository extends JpaRepository<CoachRecommendationEntity, Long> {

    List<CoachRecommendationEntity> findByFlowUser(FlowUserEntity flowUser);

    List<CoachRecommendationEntity> findByStatus(RecommendationStatus status);

    List<CoachRecommendationEntity> findByFlowUserAndStatus(
            FlowUserEntity flowUser,
            RecommendationStatus status);

    long countByFlowUser(FlowUserEntity flowUser);

    @Query("""
            SELECT COUNT(c)
            FROM CoachRecommendationEntity c
            WHERE c.flowUser = :user
            AND c.status = 'ACCEPTED'
            """)
    long countAcceptedRecommendations(
            @Param("user") FlowUserEntity user);

    List<CoachRecommendationEntity> findAllByOrderByCreatedAtDesc();

    List<CoachRecommendationEntity> findByStatusOrderByCreatedAtDesc(
            RecommendationStatus status);

}