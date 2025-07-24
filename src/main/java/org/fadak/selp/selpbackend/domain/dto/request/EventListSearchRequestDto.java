package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // @ModelAttribute 매핑
@Builder
public class EventListSearchRequestDto {

    private int year;

    private int month;
}
