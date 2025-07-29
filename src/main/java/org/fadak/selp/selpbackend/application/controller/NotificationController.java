package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.NotificationService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<?> registerNotification(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody NotificationRequestDto dto
    ) {

        long loginMemberId = userPrincipal.getId();
        notificationService.registerNotification(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendNotifications(
        @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {

        long loginMemberId = userPrincipal.getId();
        notificationService.sendScheduledNotifications();
        return ResponseEntity.ok().body("전송 완료");
    }
}
