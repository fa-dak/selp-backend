package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.GiftBundleFacadeService;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveFromCalendarRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftRecommendAgainRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleItemResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gift-bundle")
public class GiftBundleController {

    private final GiftBundleFacadeService giftBundleFacadeService;

    @PostMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestBody GiftBundleRecommendRequestDto request) {
        List<GiftBundleItemResponseDto> result = giftBundleFacadeService.recommendGiftBundle(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/recommend-again")
    public ResponseEntity<?> recommendAgain(@RequestBody GiftRecommendAgainRequestDto request) {
        GiftBundleItemResponseDto result = giftBundleFacadeService.recommendGiftBundleItem(request);
        return ResponseEntity.ok(result);
    }

    /**
     * 달력을 거치지않고 추천 받은 경우
     */
    @PostMapping
    public ResponseEntity<?> registerGiftBundle(@RequestBody GiftBundleSaveRequestDto request) {
        giftBundleFacadeService.registerGiftBundle(request, 1L);
        return ResponseEntity.ok().build();
    }

    /**
     * 달력을 거치고 추천하는 경우
     */
    @PostMapping("/calendar")
    public ResponseEntity<?> registerGiftBundleFromCalendar(@RequestBody GiftBundleSaveFromCalendarRequestDto request) {
        giftBundleFacadeService.registerGiftBundleFromCalendar(request, 1L);
        return ResponseEntity.ok().build();
    }

}
