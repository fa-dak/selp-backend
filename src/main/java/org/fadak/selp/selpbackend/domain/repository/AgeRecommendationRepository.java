package org.fadak.selp.selpbackend.domain.repository;

import org.fadak.selp.selpbackend.domain.entity.AgeRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeRecommendationRepository extends JpaRepository<AgeRecommendation, Long> {
    void deleteByAgeGroup(String ageGroup);
}
