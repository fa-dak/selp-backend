package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GiftBundleSaveFromCalendarRequestDto {
    private List<Long> giftIds;
    private Long eventId;
}
