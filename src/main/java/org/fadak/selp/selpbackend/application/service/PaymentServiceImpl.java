package org.fadak.selp.selpbackend.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fadak.selp.selpbackend.domain.constant.PayStatus;
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
            .status(PayStatus.PAID)
            .build();
        paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void cancelGiftBundle(long loginMemberId, Long giftBundleId) {

        // 상품 꾸러미 가져오기
        GiftBundle giftBundle = giftBundleService.searchById(giftBundleId, loginMemberId);
        log.info("Gift Bundle: {}", giftBundle);

        // TODO: 상품 꾸러미 결제 상태 취소로 변경하기

        // 결제 정보 가져오기
        Payment payment = paymentRepository.findByGiftBundleAndStatus(giftBundle, PayStatus.PAID)
            .orElseThrow(() -> new IllegalArgumentException("해당 상품 꾸러미가 결제 완료된 결제 정보가 없습니다."));
        log.info("Payment: {}", payment);

        // 결제 정보 삭제 (TODO: 취소할 건지 지울 건지 선택 맘대루)
//        payment.setStatus(PayStatus.CANCEL);
        paymentRepository.delete(payment);

        // Iamport에 결제 취소 요청
        portOneService.cancel(payment.getImpUid(), payment.getAmount());

    }
}
