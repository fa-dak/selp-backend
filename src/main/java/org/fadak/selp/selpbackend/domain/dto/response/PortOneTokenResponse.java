package org.fadak.selp.selpbackend.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortOneTokenResponse(
    int code,
    String message,
    Response response
) {

    public record Response(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expired_at") long expiredAt
    ) {

    }
}