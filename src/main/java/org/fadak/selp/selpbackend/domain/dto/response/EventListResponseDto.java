package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventListResponseDto {

    private long eventId;

    private String eventName;

    private String eventType;

    private String eventDate;

    private String receiverNickname;

    private Integer notificationDaysBefore;
}
