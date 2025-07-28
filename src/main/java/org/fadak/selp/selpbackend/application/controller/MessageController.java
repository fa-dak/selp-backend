package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.fadak.selp.selpbackend.domain.dto.message.MessageRequest;
import org.fadak.selp.selpbackend.domain.dto.message.MessageResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.fadak.selp.selpbackend.application.service.MessageService;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{giftBundleId}")
    public ResponseEntity<MessageResponse> recommendMessages(
            @PathVariable Long giftBundleId,
            @RequestParam String tone
    ) {
        MessageResponse response = messageService.recommendMessage(giftBundleId, tone);
        return ResponseEntity.ok(response);
    }
}