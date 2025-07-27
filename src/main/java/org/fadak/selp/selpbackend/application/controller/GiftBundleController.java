package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.GiftBundleFacadeService;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleRecommendResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gift-bundle")
public class GiftBundleController {

    private final GiftBundleFacadeService giftBundleFacadeService;

    @PostMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestBody GiftBundleRecommendRequestDto request) {
        GiftBundleRecommendResponseDto result = giftBundleFacadeService.recommendGiftBundle(request);
        return ResponseEntity.ok(result);
    }
}
