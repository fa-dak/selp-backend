package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.GiftBundleFacadeService;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleRecommendRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveFromCalendarRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftBundleSaveRequestDto;
import org.fadak.selp.selpbackend.domain.dto.request.GiftRecommendAgainRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleItemResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.service.GiftBundleService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.response.GiftBundleResponseDto;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/gift-bundle")
public class GiftBundleController {
    private final GiftBundleService giftBundleService;
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


    @GetMapping("/gift-bundles")
    public ResponseEntity<List<GiftBundleResponseDto>> getMyGiftBundles(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @SortDefault(sort = "createdDate", direction =Sort.Direction.DESC) Sort sort
    ) {
        List<GiftBundleResponseDto> giftBundles = giftBundleService.getMyGiftBundles(userPrincipal.getId(), sort);
        return ResponseEntity.ok(giftBundles);
    }

    @GetMapping("/gift-bundles/{bundleId}")
    public ResponseEntity<GiftBundleResponseDto> getMyGiftBundleDetail(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long bundleId
    ) {
        GiftBundleResponseDto giftBundle = giftBundleService.getMyGiftBundleDetail(bundleId, userPrincipal.getId());
        return ResponseEntity.ok(giftBundle);
    }
}
