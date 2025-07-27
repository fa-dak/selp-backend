package org.fadak.selp.selpbackend.application.infrastructure;

import lombok.RequiredArgsConstructor;
import org.fadak.selp.selpbackend.domain.dto.response.KakaoUserInfoResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.IllformedLocaleException;

@Component
@RequiredArgsConstructor
public class KakaoClient {
    private final RestTemplate restTemplate;
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try{
            ResponseEntity<KakaoUserInfoResponseDto> responseEntity = restTemplate.exchange(
                    KAKAO_USER_INFO_URL,
                    HttpMethod.GET,
                    requestEntity,
                    KakaoUserInfoResponseDto.class
            );
            return responseEntity.getBody();

        } catch (HttpClientErrorException e){
            throw new IllformedLocaleException("카카오 사용자 정보 가져오기 실패. : "+ e.getStatusCode());
        }
    }
}
