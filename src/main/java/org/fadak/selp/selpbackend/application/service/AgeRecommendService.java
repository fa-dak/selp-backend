package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.response.AgeRecommendResponseDto;

import java.util.List;

public interface AgeRecommendService {
    void generateAndSaveAllRecommendations();
    List<AgeRecommendResponseDto> getAgeRecommendProductList();
}
