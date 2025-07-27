package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.fadak.selp.selpbackend.domain.dto.request.NotificationRequestDto;
import org.fadak.selp.selpbackend.domain.entity.FcmToken;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.entity.Notification;
import org.fadak.selp.selpbackend.domain.repository.FcmTokenRepository;
import org.fadak.selp.selpbackend.domain.repository.MemberRepository;
import org.fadak.selp.selpbackend.domain.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final FcmTokenService fcmTokenService;

    @Override
    public void registerNotification(NotificationRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(IllegalArgumentException::new);
        Notification notification = Notification.of(member, dto);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void sendScheduledNotifications() {
        LocalDate today = LocalDate.now();
        List<Notification> notifications = notificationRepository.findBySendDateAndIsSent(today, false);

        for(Notification notification : notifications) {
            Long memberId = notification.getMember().getId();
            String token = fcmTokenRepository.findByMemberId(memberId)
                    .map(FcmToken::getToken)
                    .orElse(null);

            if(token == null) {
                log.warn("전송 실패: memberId={} → FCM 토큰 없음", memberId);
                continue;
            }

            fcmTokenService.sendNotification(token, notification.getTitle(), notification.getContent());
            notification.setIsSent(true);
        }

        log.info("FCM 알림 전송 완료: 총 {}건", notifications.size());
    }
}
