package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.NotificationFindResponseDto;

import java.util.List;

public interface NotificationService {
    void registerNotification(NotificationRequestDto dto);

    void sendScheduledNotifications();

    List<NotificationFindResponseDto> findAllNotifications(Long memberId);

}
