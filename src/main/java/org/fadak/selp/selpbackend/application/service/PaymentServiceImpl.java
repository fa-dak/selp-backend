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
    // TODO: 안되면 결제 취소되게? 아니면 다시 실행되게?
    public void validateGiftBundle(long loginMemberId, long giftBundleId, PayRequestDto request) {

        // 선물 꾸러미 가져오기
        GiftBundle giftBundle = giftBundleService.searchById(giftBundleId, loginMemberId);
        log.info("Gift Bundle: {}", giftBundle);

        // 선물 꾸러미의 최근 결제 상태가 "결제"면 결제 불가
        if (giftBundle.getCurrentPayStatus().equals(PayStatus.PAID)) {
            throw new IllegalArgumentException("선물 꾸러미가 이미 결제되었습니다.");
        }

        // 선물 꾸러미의 최근 결제 상태 "결제"로 변경
        giftBundle.setCurrentPayStatus(PayStatus.PAID);
        giftBundleService.save(giftBundle);

        // Iamport 에서 가져오기
        PortOnePaymentVerifyResponse response = portOneService.getPaymentByImpUid(
            request.getImpUid());
        log.info("Iamport Response: {}", response);
        if (!response.getStatus().equals("paid")) {
            throw new IllegalArgumentException("Gift Bundle is not paid");
        }

        // 결제 성공 정보 저장
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

        // 선물 꾸러미 가져오기
        GiftBundle giftBundle = giftBundleService.searchById(giftBundleId, loginMemberId);
        log.info("Gift Bundle: {}", giftBundle);

        // 선물 꾸러미 최근 상태가 "결제"가 아니면 예외 발생
        if (!giftBundle.getCurrentPayStatus().equals(PayStatus.PAID)) {
            throw new IllegalArgumentException("해당 선물 꾸러미의 최근 상태가 \"결제\"가 아닙니다.");
        }

        // 선물 꾸러미의 최근 결제 상태를 "취소"로 변경
        giftBundle.setCurrentPayStatus(PayStatus.CANCEL);
        giftBundleService.save(giftBundle);

        // 해당 선물 꾸러미에 해당하는 최근 결제 정보 가져오기
        Payment currentPayment = paymentRepository.findTopByGiftBundleOrderByCreatedDateDesc(
                giftBundle)
            .orElseThrow(() -> new IllegalArgumentException("해당 선물 꾸러미와 관련된 결제 정보가 없습니다."));

        // 최근 결제 정보가 "실패"면 예외
        if (currentPayment.getStatus().equals(PayStatus.CANCEL)) {
            throw new IllegalArgumentException("이미 최근에 결제 취소된 상품 꾸러미입니다.");
        }

        // 결제 취소 정보 저장
        Payment newPayment = Payment.builder()
            .giftBundle(giftBundle)
            .impUid(currentPayment.getImpUid())
            .amount(currentPayment.getAmount())
            .status(PayStatus.CANCEL)
            .build();
        paymentRepository.save(newPayment);

        // Iamport에 결제 취소 요청 (꼭!!!!!! 트랜잭션의 마지막에 있어야 함)
        portOneService.cancel(currentPayment.getImpUid(), currentPayment.getAmount());
    }
}
