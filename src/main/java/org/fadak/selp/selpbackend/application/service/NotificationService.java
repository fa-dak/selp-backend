package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;

public interface NotificationService {
    void registerNotification(NotificationRequestDto dto);
    void sendScheduledNotifications();

}
