package org.fadak.selp.selpbackend.domain.dto.response;

public record PortOnePaymentRawResponse(
    Integer code,
    String message,
    PortOnePaymentVerifyResponse response
) {

}