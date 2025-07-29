package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.service.PaymentService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.request.PayRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/gift-bundles/{gift-bundle-id}/verify")
    public ResponseEntity<?> payProduct(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable("gift-bundle-id") Long giftBundleId,
        @RequestBody PayRequestDto request
    ) {

        log.info("상품 번들 결제 요청:: {}", request);
        long loginMemberId = userPrincipal.getId();

        paymentService.validateGiftBundle(loginMemberId, giftBundleId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/gift-bundles/{gift-bundle-id}/cancel")
    public ResponseEntity<?> cancelProduct(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable("gift-bundle-id") Long giftBundleId
    ) {

        long loginMemberId = userPrincipal.getId();
        paymentService.cancelGiftBundle(loginMemberId, giftBundleId);
        return ResponseEntity.ok().build();
    }
}
