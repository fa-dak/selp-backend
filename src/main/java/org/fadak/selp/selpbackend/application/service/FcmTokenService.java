package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.request.FcmTokenRegisterRequestDto;

public interface FcmTokenService {
    void saveOrUpdateToken(Long memberId, FcmTokenRegisterRequestDto request);
    void sendNotification(String fcmToken, String title, String content);
}