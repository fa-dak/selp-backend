package org.fadak.selp.selpbackend.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PortOneCancelRequest {

    @JsonProperty("imp_uid")
    private String impUid;

    @JsonProperty("amount")
    private int amount;

    public PortOneCancelRequest(@JsonProperty("imp_uid") String impUid,
        @JsonProperty("amount") int amount) {

        this.impUid = impUid;
        this.amount = amount;
    }
}