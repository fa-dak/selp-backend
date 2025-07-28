package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class GiftBundleController {
    private final GiftBundleService giftBundleService;

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
