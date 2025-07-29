package org.fadak.selp.selpbackend.domain.dto.response;

public record PortOnePaymentRawResponse(
    int code,
    String message,
    PortOnePaymentVerifyResponse response
) {

}