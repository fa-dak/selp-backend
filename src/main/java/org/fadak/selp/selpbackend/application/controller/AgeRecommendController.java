package org.fadak.selp.selpbackend.application.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.service.AgeRecommendService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.response.AgeRecommendResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agerecommend")
public class AgeRecommendController {

    private final AgeRecommendService ageRecommendService;

    @GetMapping
    public List<AgeRecommendResponseDto> getProductList(
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {

        long loginMemberId = userPrincipal.getId();
        return ageRecommendService.getAgeRecommendProductList();
    }

    @PostMapping("/generate")
    public void generateAll(
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {

        long loginMemberId = userPrincipal.getId();
        ageRecommendService.generateAndSaveAllRecommendations();
    }
}
