package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.dto.request.PayRequestDto;
import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentVerifyResponse;
import org.fadak.selp.selpbackend.domain.entity.GiftBundle;
import org.fadak.selp.selpbackend.domain.entity.Payment;
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
    public void validateGiftBundle(long loginMemberId, long giftBundleId, PayRequestDto request) {

        // 상품 꾸러미 가져오기
        GiftBundle giftBundle = giftBundleService.searchById(giftBundleId, loginMemberId);
        log.info("Gift Bundle: {}", giftBundle);

        // TODO: 상품꾸러미에 결제 여부 보기
        // TODO: 이미 결제한 물품이면 결제 X (예외 처리)

        // Iamport 에서 가져오기
        PortOnePaymentVerifyResponse response = portOneService.getPaymentByImpUid(
            request.getImpUid());
        log.info("Iamport Response: {}", response);
        if (!response.getStatus().equals("paid")) {
            throw new IllegalArgumentException("Gift Bundle is not paid");
        }

        // 결제 DB에 결제 정보 저장
        Payment payment = Payment.builder()
            .giftBundle(giftBundle)
            .impUid(response.getImpUid())
            .amount(response.getAmount())
            .build();
        paymentRepository.save(payment);
    }
}
