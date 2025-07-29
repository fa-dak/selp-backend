package org.fadak.selp.selpbackend.domain.dto.response;

public record PortOnePaymentCancelRawResponse(
    Integer code,
    String message,
    PortOnePaymentCancelResponse response
) {

}
