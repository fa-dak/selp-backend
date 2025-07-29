package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.FcmTokenService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.request.FcmTokenRegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fcmToken")
@RequiredArgsConstructor
public class FcmTokenController {

    private final FcmTokenService fcmTokenService;

    @PostMapping
    public ResponseEntity<Void> registerFcmToken(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody FcmTokenRegisterRequestDto request
    ) {

        long loginMemberId = userPrincipal.getId();
        fcmTokenService.saveOrUpdateToken(loginMemberId, request);
        return ResponseEntity.ok().build();
    }
}

