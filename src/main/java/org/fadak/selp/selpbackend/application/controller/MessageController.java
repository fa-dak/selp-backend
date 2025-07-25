package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.message.MessageRequest;
import org.fadak.selp.selpbackend.domain.dto.message.MessageResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.fadak.selp.selpbackend.application.service.MessageService;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/recommend")
    public ResponseEntity<MessageResponse> recommend(@RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.recommendMessage(request));
    }
}