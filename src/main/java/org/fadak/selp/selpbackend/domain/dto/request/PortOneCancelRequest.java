package org.fadak.selp.selpbackend.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PortOneCancelRequest {

    @JsonProperty("imp_uid")
    private String impUid;

    private int amount;
}