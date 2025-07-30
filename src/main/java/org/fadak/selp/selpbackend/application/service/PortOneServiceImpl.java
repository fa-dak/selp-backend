package org.fadak.selp.selpbackend.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.PortOneCancelRequest;
import org.fadak.selp.selpbackend.domain.dto.request.PortOneTokenRequest;
import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentCancelRawResponse;
import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentCancelResponse;
import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentRawResponse;
import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentVerifyResponse;
import org.fadak.selp.selpbackend.domain.dto.response.PortOneTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortOneServiceImpl implements PortOneService {

    private final RestTemplate restTemplate;

    @Value("${iamport.key}")
    private String iamportKey;

    @Value("${iamport.secret}")
    private String iamportSecret;

    @Override
    public PortOnePaymentVerifyResponse getPaymentByImpUid(String impUid) {

        String token = getPortOneAccessToken();
        log.info("Access 토큰 발급 완료: {}", token);
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<PortOnePaymentRawResponse> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            requestEntity,
            PortOnePaymentRawResponse.class
        );

        PortOnePaymentRawResponse body = response.getBody();
        if (body == null || body.code() != 0 || body.response() == null) {
            throw new IllegalArgumentException(
                "결제 정보 조회 실패: " + (body != null ? body.message() : "응답 없음"));
        }

        return body.response();
    }

    @Override
    public PortOnePaymentCancelResponse cancel(String impUid, int amount) {

        String token = getPortOneAccessToken();
        log.info("Access 토큰 발급 완료: {}", token);
        String url = "https://api.iamport.kr/payments/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        PortOneCancelRequest cancelRequest = new PortOneCancelRequest(impUid, amount);
        HttpEntity<PortOneCancelRequest> requestEntity = new HttpEntity<>(cancelRequest, headers);

        ResponseEntity<PortOnePaymentCancelRawResponse> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
            PortOnePaymentCancelRawResponse.class
        );

        PortOnePaymentCancelRawResponse body = response.getBody();
        if (body == null || body.code() != 0 || body.response() == null) {
            throw new IllegalArgumentException(
                "결제 취소 실패: " + (body != null ? body.message() : "응답 없음"));
        }

        return body.response();
    }

    private String getPortOneAccessToken() {

        String url = "https://api.iamport.kr/users/getToken";

        PortOneTokenRequest requestBody = new PortOneTokenRequest(iamportKey, iamportSecret);

        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info("보낼 JSON: {}",
                mapper.writeValueAsString(new PortOneTokenRequest(iamportKey, iamportSecret)));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<PortOneTokenRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<PortOneTokenResponse> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
            PortOneTokenResponse.class
        );

        PortOneTokenResponse body = response.getBody();
        if (body == null || body.code() != 0 || body.response() == null) {
            throw new IllegalArgumentException(
                "액세스 토큰 발급 실패: " + (body != null ? body.message() : "응답 없음"));
        }

        return body.response().accessToken();
    }
}
