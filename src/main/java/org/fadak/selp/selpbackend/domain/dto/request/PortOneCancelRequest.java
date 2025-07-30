package org.fadak.selp.selpbackend.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortOneCancelRequest(
    @JsonProperty("imp_uid") String impUid,
    int amount
) {

}