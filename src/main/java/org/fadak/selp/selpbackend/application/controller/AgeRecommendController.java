package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.service.AgeRecommendService;
import org.fadak.selp.selpbackend.domain.dto.response.AgeRecommendResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agerecommend")
public class AgeRecommendController {

    private final AgeRecommendService ageRecommendService;

    @GetMapping
    public List<AgeRecommendResponseDto> getProductList() {
        return ageRecommendService.getAgeRecommendProductList();
    }

    @PostMapping("/generate")
    public void generateAll() {
        ageRecommendService.generateAndSaveAllRecommendations();
    }
}
