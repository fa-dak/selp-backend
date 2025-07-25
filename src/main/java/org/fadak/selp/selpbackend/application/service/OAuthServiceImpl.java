package org.fadak.selp.selpbackend.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.application.infrastructure.KakaoClient;
import org.fadak.selp.selpbackend.application.security.JwtTokenProvider;
import org.fadak.selp.selpbackend.domain.dto.request.KakaoLoginRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.KakaoUserInfoResponseDto;
import org.fadak.selp.selpbackend.domain.dto.response.LoginResponseDto;
import org.fadak.selp.selpbackend.domain.entity.Member;
import org.fadak.selp.selpbackend.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final MemberRepository memberRepository;
    private final KakaoClient kakaoClient;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public LoginResponseDto loginWithKakao(KakaoLoginRequestDto loginRequestDto) {
        KakaoUserInfoResponseDto kakaoUserInfo = kakaoClient.getUserInfo(loginRequestDto.getAccessToken());

        Member member = memberRepository.findByProviderAndEmail("KAKAO", kakaoUserInfo.getKakaoAccount().getEmail()).orElseGet(() ->{
            Member newMember = Member.builder()
                    .email(kakaoUserInfo.getKakaoAccount().getEmail())
                    .nickname(kakaoUserInfo.getKakaoAccount().getProfile().getNickname())
                    .provider("KAKAO")
                    .build();
            return memberRepository.save(newMember);
        });

        String accessToken = jwtTokenProvider.createAccessToken(member.getId());

        // 리프레시 토큰은 사용되진 않음
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
