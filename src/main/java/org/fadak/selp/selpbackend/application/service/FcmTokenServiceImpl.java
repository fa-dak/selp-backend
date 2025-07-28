package org.fadak.selp.selpbackend.application.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.FcmTokenRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.entity.FcmToken;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.repository.FcmTokenRepository;
import org.fadak.selp.selpbackend.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FcmTokenServiceImpl implements FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;
    private final MemberRepository memberRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Override
    @Transactional
    public void saveOrUpdateToken(Long memberId, FcmTokenRegisterRequestDto request) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Optional<FcmToken> optionalToken = fcmTokenRepository.findByMemberId(memberId);

        if (optionalToken.isEmpty()) {
            FcmToken newToken = FcmToken.of(member, request.getToken(), request.getDeviceType());
            fcmTokenRepository.save(newToken);
            return;
        }

        FcmToken existingToken = optionalToken.get();
        existingToken.updateToken(request.getToken(), request.getDeviceType());
    }

    @Override
    @Transactional
    public void sendNotification(String fcmToken, String title, String content) {
        try {
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(content)
                    .build();

            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(notification)
                    .build();

            String response = firebaseMessaging.send(message);
            log.info("FCM 전송 성공 | token={}, response={}", fcmToken, response);

        } catch (Exception e) {
            log.warn("FCM 전송 실패 | token={}, title={}, error={}", fcmToken, title, e.getMessage());
        }
    }
}
