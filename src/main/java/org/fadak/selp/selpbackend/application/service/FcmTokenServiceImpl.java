package org.fadak.selp.selpbackend.application.service;

import lombok.AllArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.request.FcmTokenRegisterRequestDto;
import org.fadak.selp.selpbackend.domain.entity.FcmToken;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.repository.FcmTokenRepository;
import org.fadak.selp.selpbackend.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FcmTokenServiceImpl implements FcmTokenService {

    private final FcmTokenRepository fcmTokenRepository;
    private final MemberRepository memberRepository;

    @Override
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
}
