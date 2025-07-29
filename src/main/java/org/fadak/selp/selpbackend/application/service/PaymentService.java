package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.request.PayRequestDto;

public interface PaymentService {

    void validateGiftBundle(long loginMemberId, Long giftBundleId, PayRequestDto request);
}
