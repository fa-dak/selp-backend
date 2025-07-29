package org.fadak.selp.selpbackend.application.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.application.service.OAuthService;
import org.fadak.selp.selpbackend.domain.auth.UserPrincipal;
import org.fadak.selp.selpbackend.domain.dto.request.KakaoLoginRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/kakao")
    public ResponseEntity<LoginResponseDto> loginWithKakao(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @RequestBody KakaoLoginRequestDto request
    ) {

        long loginMemberId = userPrincipal.getId();
        log.info("카카오 로그인 요청이 들어왔습니다. 인가 코드: {}", request.getAccessToken());
        LoginResponseDto responseDto = oAuthService.loginWithKakao(request);
        return ResponseEntity.ok(responseDto);
    }
}