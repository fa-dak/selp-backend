package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.FcmTokenService;
import org.fadak.selp.selpbackend.domain.dto.request.FcmTokenRegisterRequestDto;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> registerFcmToken(@RequestBody FcmTokenRegisterRequestDto request) {
        long memberId = 1L;
        fcmTokenService.saveOrUpdateToken(memberId, request);
        return ResponseEntity.ok().build();
    }
}

