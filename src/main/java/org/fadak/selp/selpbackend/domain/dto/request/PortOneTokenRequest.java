package org.fadak.selp.selpbackend.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PortOneTokenRequest {

    @JsonProperty("imp_key")
    private String impKey;

    @JsonProperty("imp_secret")
    private String impSecret;

    public PortOneTokenRequest(
        @JsonProperty("imp_key") String impKey,
        @JsonProperty("imp_secret") String impSecret) {

        this.impKey = impKey;
        this.impSecret = impSecret;
    }
}