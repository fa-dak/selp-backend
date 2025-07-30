package org.fadak.selp.selpbackend.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PortOneTokenRequest {

    @JsonProperty("imp_key")
    private String impKey;

    @JsonProperty("imp_secret")
    private String impSecret;
}