package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.MessageService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.message.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{gift-bundle-id}")
    public ResponseEntity<MessageResponse> recommendMessages(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable("gift-bundle-id") Long giftBundleId,
        @RequestParam String tone
    ) {

        long loginMemberId = userPrincipal.getId();
        MessageResponse response = messageService.recommendMessage(giftBundleId, tone);
        return ResponseEntity.ok(response);
    }
}