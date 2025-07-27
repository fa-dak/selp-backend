package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.request.KakaoLoginRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.LoginResponseDto;

public interface OAuthService {
    LoginResponseDto loginWithKakao(KakaoLoginRequestDto loginRequestDto);
}
