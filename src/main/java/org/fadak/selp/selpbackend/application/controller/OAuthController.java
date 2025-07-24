package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.service.OAuthService;
import org.fadak.selp.selpbackend.domain.dto.request.KakaoLoginRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oAuthService;

    @PostMapping("/kakao")
    public ResponseEntity<LoginResponseDto> loginWithKakao(@RequestBody KakaoLoginRequestDto kakaoLoginRequestDto) {
        LoginResponseDto responseDto = oAuthService.loginWithKakao(kakaoLoginRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
