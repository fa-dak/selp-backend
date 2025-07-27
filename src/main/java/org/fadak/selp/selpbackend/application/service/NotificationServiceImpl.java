package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.entity.Notification;
import org.fadak.selp.selpbackend.domain.repository.MemberRepository;
import org.fadak.selp.selpbackend.domain.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Override
    public void registerNotification(NotificationRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(IllegalArgumentException::new);
        Notification notification = Notification.of(member, dto);
        notificationRepository.save(notification);
    }

    @Override
    public void sendScheduledNotifications() {

    }
}
