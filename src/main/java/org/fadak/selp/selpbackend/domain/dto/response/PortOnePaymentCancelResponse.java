package org.fadak.selp.selpbackend.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortOnePaymentCancelResponse(
    @JsonProperty("imp_uid") String impUid,
    @JsonProperty("merchant_uid") String merchantUid
) {

}
