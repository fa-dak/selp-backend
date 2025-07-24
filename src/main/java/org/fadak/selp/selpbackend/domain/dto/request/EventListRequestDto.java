package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventListRequestDto {

    private String date; // YYYY-MM-DD
}
