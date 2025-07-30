package org.fadak.selp.selpbackend.application.service;

import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentCancelResponse;
import org.fadak.selp.selpbackend.domain.dto.response.PortOnePaymentVerifyResponse;

public interface PortOneService {

    PortOnePaymentVerifyResponse getPaymentByImpUid(String impUid);

    PortOnePaymentCancelResponse cancel(String impUid, int amount);
}
