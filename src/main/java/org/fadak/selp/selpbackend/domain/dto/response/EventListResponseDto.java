package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.fadak.selp.selpbackend.domain.constant.EventType;

@Getter
@Builder
public class EventListResponseDto {

    private long eventId;

    private String eventName;

    private EventType eventType;

    private String eventDate;

    private long receiverInfoId;

    private String receiverNickname;

    private Integer notificationDaysBefore;
}
