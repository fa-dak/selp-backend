package org.fadak.selp.selpbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.fadak.selp.selpbackend.domain.constant.EventType;

@Getter
@Setter // JSON 매핑
@AllArgsConstructor
public class EventModifyRequestDto {

    private String eventDate; // 형식: YYYY-MM-DD

    private String eventName;

    private EventType eventType;

    private long receiverInfoId;

    private Integer notificationDaysBefore; // null 가능
}
