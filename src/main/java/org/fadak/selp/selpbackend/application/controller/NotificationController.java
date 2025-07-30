package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.NotificationService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.NotificationFindResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/unread-count")
    public ResponseEntity<?> unreadNotificationCount(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        int result = notificationService.findUnreadNotificationsCount(userPrincipal.getId());
        return ResponseEntity.ok(result);
    }


    @GetMapping
    public ResponseEntity<?> getNotifications(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<NotificationFindResponseDto> result = notificationService.findAllNotifications(userPrincipal.getId());
        return ResponseEntity.ok(result);
    }
}
