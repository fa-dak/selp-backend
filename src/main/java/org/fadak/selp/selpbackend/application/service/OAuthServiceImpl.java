package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.request.KakaoLoginRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.LoginResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
    @Override
    public LoginResponseDto loginWithKakao(KakaoLoginRequestDto loginRequestDto) {
        return null;
    }
}
