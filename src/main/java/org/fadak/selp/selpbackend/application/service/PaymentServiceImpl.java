package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.PayRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentVerifyResponse;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final GiftBundleService giftBundleService;
    private final PortOneService portOneService;

    @Override
    @Transactional
    public void validateGiftBundle(long loginMemberId, Long giftBundleId, PayRequestDto request) {

        // 상품 꾸러미 가져오기
        GiftBundle giftBundle = giftBundleService.searchById(giftBundleId, loginMemberId);

        // Iamport 에서 가져오기
        PortOnePaymentVerifyResponse response = portOneService.getPaymentByImpUid(
            request.getImpUid());
        log.info("Iamport Response: {}", response);
    }
}
