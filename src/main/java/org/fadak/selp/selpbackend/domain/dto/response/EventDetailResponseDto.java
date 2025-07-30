package org.fadak.selp.selpbackend.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fadak.selp.selpbackend.domain.constant.EventType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailResponseDto {

    private long eventId;

    private String eventName;

    private EventType eventType;
    
    private String eventDate;

    private long receiverId;

    private String receiverNickname;

    private Integer notificationDaysBefore;
}
